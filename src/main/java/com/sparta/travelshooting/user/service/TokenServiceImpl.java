package com.sparta.travelshooting.user.service;

import com.sparta.travelshooting.jwt.JwtUtil;
import com.sparta.travelshooting.user.dto.TokenResponseDto;
import com.sparta.travelshooting.user.entity.RefreshToken;
import com.sparta.travelshooting.user.entity.RoleEnum;
import com.sparta.travelshooting.user.entity.User;
import com.sparta.travelshooting.user.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenServiceImpl implements TokenService{

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
        String req2 = jwtUtil.getTokenFromRequest(req);
        log.info(req2);
        RefreshToken refreshToken = refreshTokenRepository.findByAccessToken(req2).orElseThrow(() -> new IllegalArgumentException("Token not found"));

        String validateToken = jwtUtil.substringToken(refreshToken.getTokenValue());
        if (!jwtUtil.validateRefreshToken(validateToken)) {
            throw new IllegalArgumentException("로그인을 새로 해주세요.");
        };

        // 현재 쿠키 삭제
        jwtUtil.deleteCookie(jwtUtil.getTokenFromRequest(req), res);

        Claims claims = jwtUtil.getUserInfoFromRefreshToken(validateToken);
        String token = jwtUtil.createAccessToken(claims.getSubject(), (RoleEnum) claims.get("AUTHORIZATION_KEY"));
        refreshToken.update(token);

        jwtUtil.addJwtToCookie(token, res);
        return new TokenResponseDto(refreshToken.getTokenValue(), token);
    }
}