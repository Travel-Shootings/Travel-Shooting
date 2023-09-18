package com.sparta.travelshooting.user.service;

import com.sparta.travelshooting.jwt.JwtUtil;
import com.sparta.travelshooting.user.dto.TokenResponseDto;
import com.sparta.travelshooting.user.entity.RefreshToken;
import com.sparta.travelshooting.user.entity.User;
import com.sparta.travelshooting.user.repository.RefreshTokenRepository;
import com.sparta.travelshooting.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenServiceImpl implements TokenService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    /**
     * 1. 전달받은 유저의 아이디로 유저가 존재하는지 확인
     * 2. RefreshToken이 유효한지 체크
     * 3. AccessToken을 발급한 뒤 쿠키에 추가
     */
    @Override
    @Transactional
    public TokenResponseDto requestRefreshToken(HttpServletResponse res, HttpServletRequest req) {

        String refreshTokenUUID = jwtUtil.getUUID(req);
        RefreshToken refreshToken = refreshTokenRepository.findById(refreshTokenUUID).orElseThrow(() -> new IllegalArgumentException("Token not found"));

        // 현재 쿠키 삭제
        jwtUtil.deleteCookieWithAccessToken(jwtUtil.getTokenFromRequest(req), res);

        //유저의 정보 가져오기 (엑세스 토큰을 새로 발급하기 위해서)
        Long userId = refreshToken.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Not found user"));

        // 새로운 엑세스 토큰을 만들고 쿠키에 추가
        String token = jwtUtil.createAccessToken(user.getEmail(), user.getRole());
        jwtUtil.addJwtToCookie(token, res);

        return new TokenResponseDto(refreshToken.getRefreshToken(), token);
    }
}
