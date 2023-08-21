package com.sparta.travelshooting.user.service;

import com.sparta.travelshooting.jwt.JwtUtil;
import com.sparta.travelshooting.user.dto.LoginRequestDto;
import com.sparta.travelshooting.user.dto.SignupRequestDto;
import com.sparta.travelshooting.user.entity.*;
import com.sparta.travelshooting.user.repository.RefreshTokenRepository;
import com.sparta.travelshooting.user.repository.TokenBlackListRepository;
import com.sparta.travelshooting.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenBlackListRepository tokenBlackListRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public void signup(SignupRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String nickname = requestDto.getNickname();
        RegionEnum region = RegionEnum.valueOf(requestDto.getRegion());
        log.info("region : " + region);

        // 가입된 정보 확인 -> email과 nickname은 중복 x
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        } else if (userRepository.findByNickname(nickname).isPresent()) {
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        }

        /**
         * role을 USER로 고정
         * 실제 서비스에서는 회원가입 시 USER or ADMIN을 선택하는 경우가 흔치 않음
         * 배운 방법은 관리자 암호가 코드에 노출되어 있기 때문에 사용하기 꺼려짐
         * -> ADMIN계정을 하나만 sql을 활용해서 가입시키고 그 ADMIN계정으로 관리하는 방법을 사용
         * -> 우선, 모든 계정은 모두 일반 유저로 회원가입
         */
        RoleEnum role = RoleEnum.USER;

        // 사용자 정보 DB에 저장
        User user = new User(requestDto, password, region, role);
        userRepository.save(user);
    }

    @Override
    public void login(LoginRequestDto requestDto, HttpServletResponse res) {
        // 이메일 확인
        User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("가입되지 않은 이메일입니다.")
        );

        // 비밀번호 확인
        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

        // Jwt 토큰 생성
        String token = jwtUtil.createAccessToken(requestDto.getEmail(), user.getRole());

        // TODO : redis를 이용해서 저장하는 방법 공부
        // 우선은 DB에 저장
        String refreshTokenValue = jwtUtil.createRefreshToken(requestDto.getEmail(), user.getRole());
        RefreshToken refreshToken = new RefreshToken(user.getId(), refreshTokenValue);
        refreshTokenRepository.save(refreshToken);

        // AccessToken은 쿠키에 추가
        jwtUtil.addJwtToCookie(token, res);
    }

    /**
     * 1. Refresh Token 삭제
     * 2. Access Token 블랙리스트에 추가
     *      -> 만료기한이 지난 AccessToken 데이터 정리는 스케줄링을 이용해보자
     * 3. 쿠키 삭제
     */
    @Override
    public void logout(User user, HttpServletRequest req, HttpServletResponse res) {
        // Refresh Token 삭제
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(user.getId()).orElseThrow(() -> new NullPointerException("Not Found User"));
        refreshTokenRepository.delete(refreshToken);

        // Access Token 블랙리스트에 추가
        String accessToken = jwtUtil.getTokenFromRequest(req);

        Date expireationDate = jwtUtil.extractExpirationDateFromToken(jwtUtil.substringToken(accessToken));

        TokenBlackList tokenBlackList = new TokenBlackList(accessToken, expireationDate);
        tokenBlackListRepository.save(tokenBlackList);

        // 쿠키 삭제
        jwtUtil.deleteCookie(accessToken, res);
    }
}
