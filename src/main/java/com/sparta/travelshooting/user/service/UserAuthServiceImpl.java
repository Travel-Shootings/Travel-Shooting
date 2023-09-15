package com.sparta.travelshooting.user.service;

import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.jwt.JwtUtil;
import com.sparta.travelshooting.redis.RedisUtil;
import com.sparta.travelshooting.user.dto.LoginRequestDto;
import com.sparta.travelshooting.user.dto.SignupRequestDto;
import com.sparta.travelshooting.user.entity.RefreshToken;
import com.sparta.travelshooting.user.entity.RegionEnum;
import com.sparta.travelshooting.user.entity.RoleEnum;
import com.sparta.travelshooting.user.entity.User;
import com.sparta.travelshooting.user.repository.RefreshTokenRepository;
import com.sparta.travelshooting.user.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisUtil redisUtil;
    private final JwtUtil jwtUtil;

    @Override
    public ApiResponseDto signup(SignupRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String nickname = requestDto.getNickname();
        RegionEnum region = RegionEnum.valueOf(requestDto.getRegion());

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

        return new ApiResponseDto("회원가입이 되었습니다.", HttpStatus.CREATED.value());
    }

    @Override
    public ApiResponseDto login(LoginRequestDto requestDto, HttpServletResponse res) {
        // 이메일 확인
        User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("가입되지 않은 이메일입니다.")
        );

        // 비밀번호 확인
        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

        String refreshTokenValue = UUID.randomUUID().toString();
        RefreshToken refreshToken = new RefreshToken(user.getId(), refreshTokenValue);
        refreshTokenRepository.save(refreshToken);

        // refresh token 쿠키에 추가
        Cookie cookie = new Cookie("refreshToken", refreshTokenValue);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        res.addCookie(cookie);

        // Jwt 토큰 생성
        String token = jwtUtil.createAccessToken(requestDto.getEmail(), user.getRole());

        // AccessToken은 쿠키에 추가
        jwtUtil.addJwtToCookie(token, res);

        return new ApiResponseDto("로그인이 완료되었습니다.", HttpStatus.OK.value());
    }

    /**
     * 1. Refresh Token 삭제
     * 2. Access Token 블랙리스트에 추가
     * 3. 쿠키 삭제
     */
    @Override
    public ApiResponseDto logout(HttpServletRequest req, HttpServletResponse res) {
        // Refresh Token 삭제
        String refreshTokenUUID = jwtUtil.getUUID(req);
        RefreshToken refreshToken = refreshTokenRepository.findById(refreshTokenUUID).orElseThrow(() -> new IllegalArgumentException("Token not found"));
        refreshTokenRepository.delete(refreshToken);

        // Access Token 블랙리스트에 추가
        String accessToken = jwtUtil.getTokenFromRequest(req);
        Date expirationDate = jwtUtil.extractExpirationDateFromToken(jwtUtil.substringToken(accessToken));
        Long expirationDateMill = expirationDate.getTime() - System.currentTimeMillis(); // 토큰 만료 시간 - 현재 시간
        redisUtil.setBlackList(accessToken, "AccessToken", expirationDateMill);

        // 엑세스 토큰이 담겨있는 쿠키 삭제
        jwtUtil.deleteCookieWithAccessToken(accessToken, res);

        // 리프레시 토큰이 담겨있는 쿠키 삭제
        Cookie cookie = new Cookie("refreshToken", refreshTokenUUID);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        res.addCookie(cookie);

        return new ApiResponseDto("로그아웃 성공", HttpStatus.OK.value());
    }

    // email 인증
    private final JavaMailSender javaMailSender;
    private static final String senderMail = "TravelShooting";
    private static final Long expAuthNumber = 10 * 60 * 1000L; //10분
    @Override
    @Transactional(timeout = 15)
    public ApiResponseDto sendMail(String email) {
        int authNumber = (int)(Math.random() * (90000)) + 10000; // (int) Math.random() * (최댓값-최소값+1) + 최소값

        // 메일 메세지
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            message.setFrom(senderMail);
            message.setRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject("Travel-Shooting 이메일 인증");
            String body = "";
            body += "<h3>" + "안녕하세요. Travel-Shooting 입니다." + "</h3>";
            body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
            body += "<h1>" + authNumber + "</h1>";
            body += "<h3>" + "위의 인증 번호를 입력해주세요." + "</h3>";
            message.setText(body,"UTF-8", "html");
            javaMailSender.send(message);

            log.info("메일 전송 후");
            redisUtil.setAuthNumber(authNumber, "AuthNumber", expAuthNumber);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return new ApiResponseDto("인증 메일을 보냈습니다. 10분 안에 인증을 완료해주세요.", HttpStatus.OK.value());
    }

    @Override
    public ApiResponseDto confirmAuthNumber(String authNumber) {
        if (redisUtil.hasAuthNumber(authNumber)) {
            return new ApiResponseDto("인증에 성공했습니다.", HttpStatus.OK.value());
        }
        return new ApiResponseDto("인증에 실패했습니다. 다시 시도해주세요.", HttpStatus.BAD_REQUEST.value());
    }

}
