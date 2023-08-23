package com.sparta.travelshooting.jwt;

import com.sparta.travelshooting.redis.RedisUtil;
import com.sparta.travelshooting.user.entity.RoleEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUtil {
    private final RedisUtil redisUtil;
    // 토큰 생성에 필요한 값
    // Header Authorization KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // 사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "auth";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";
    // 토큰 만료시간
    private final long TOKEN_TIME = 30 * 60 * 1000L; // 30분

    // Base64 Encode 한 SecretKey
    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;

    // 객체 초기화: secretKey 를 Base64 로 디코드한다.
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // 토큰 생성
    // secretKey = 토큰에 서명하기 위해 사용하는 키
    // email = 앞으로 유저 정보를 포함한 메서드를 실행시킬 때마다 토큰에서 꺼내서 인증 확인
    public String createAccessToken(String email, RoleEnum role) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(email) // 사용자 식별자값(ID), 공간에 email 을 넣어 줌
                        .claim(AUTHORIZATION_KEY, role) // 공간 속에 권한 키값과 사용자 권한을 담는다
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact(); // String 형식의 JWT 토큰으로 반환됨
    }

    // JWT Cookie 에 저장
    public void addJwtToCookie(String token, HttpServletResponse res) {
        try {
            token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 %20으로 바꾸어준 뒤 encoding 진행

            Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token); // Name-Value
            cookie.setPath("/");

            // Response 객체에 Cookie 추가
            res.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {

        }
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    // 비밀 값으로 복호화
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            // 블랙리스트에 있는 토큰인지 확인
            if(redisUtil.hasKeyBlackList(token)) {
                throw new IllegalArgumentException("사용할 수 없는 토큰입니다. 다시 로그인 해주세요.");
            }

            return true;
        } catch (ExpiredJwtException e) {
            return false;
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String getTokenFromRequest(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(AUTHORIZATION_HEADER)) {
                    try {
                        return URLDecoder.decode(cookie.getValue(), "UTF-8"); // Encode 되어 넘어간 Value 다시 Decode
                    } catch (UnsupportedEncodingException e) {
                        return null;
                    }
                }
            }
        }
        return null;
    }

    //토큰에서 만료 시간 가져오기
    public Date extractExpirationDateFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key) // 토큰 검증을 위해 사용한 키
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getExpiration(); // 토큰의 만료 시간을 반환
        } catch (SignatureException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        throw new NullPointerException("Not Found Token");
    }

    public void deleteCookieWithAccessToken(String token, HttpServletResponse res) {
        try {
            token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행

            Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token); // Name-Value
            cookie.setPath("/");
            cookie.setMaxAge(0);

            // Response 객체에 Cookie 추가
            res.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("에러가 발생했습니다.");
        }
    }

    //refresh token의 값 가져오기
    public String getUUID(HttpServletRequest req) {
        try {
            Cookie[] cookies = req.getCookies();

            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    return cookie.getValue();
                }
            }
            return null;
        } catch (Exception e) {
            throw new IllegalArgumentException("문제가 발생했습니다.");
        }
    }

}
