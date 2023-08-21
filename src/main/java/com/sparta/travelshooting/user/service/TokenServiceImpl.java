package com.sparta.travelshooting.user.service;

import com.sparta.travelshooting.jwt.JwtUtil;
import com.sparta.travelshooting.user.dto.TokenResponseDto;
import com.sparta.travelshooting.user.entity.RefreshToken;
import com.sparta.travelshooting.user.entity.User;
import com.sparta.travelshooting.user.repository.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService{

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    /**
     * 1. 전달받은 유저의 아이디로 유저가 존재하는지 확인
     * 2. RefreshToken이 유효한지 체크
     * 3. AccessToken을 발급한 뒤 쿠키에 추가
    */
    @Override
    public TokenResponseDto requestRefreshToken(User user, HttpServletResponse res) {
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(user.getId()).orElseThrow(() -> new IllegalArgumentException("Token not found"));

        String validateToken = jwtUtil.substringToken(refreshToken.getTokenValue());
        if (!jwtUtil.validateRefreshToken(validateToken, res)) {
            throw new IllegalArgumentException("로그인을 새로 해주세요.");
        };

        String token = jwtUtil.createAccessToken(user.getEmail(), user.getRole());
        jwtUtil.addJwtToCookie(token, res);

        return new TokenResponseDto(refreshToken.getTokenValue(), token);
    }
}
