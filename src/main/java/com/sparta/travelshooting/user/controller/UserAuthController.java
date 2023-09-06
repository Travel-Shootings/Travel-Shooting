package com.sparta.travelshooting.user.controller;

import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.redis.RedisUtil;
import com.sparta.travelshooting.user.dto.LoginRequestDto;
import com.sparta.travelshooting.user.dto.SignupRequestDto;
import com.sparta.travelshooting.user.dto.TokenRequestDto;
import com.sparta.travelshooting.user.dto.TokenResponseDto;
import com.sparta.travelshooting.user.service.TokenService;
import com.sparta.travelshooting.user.service.UserAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "사용자 인증 관련 API")
public class UserAuthController {
    private final UserAuthService userService;
    private final TokenService tokenService;


    // 회원가입 API
    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        ApiResponseDto apiResponseDto = userService.signup(requestDto);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.CREATED);
    }

    // 로그인 API
    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse res) {
        ApiResponseDto apiResponseDto = userService.login(requestDto, res);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    /**
     * 로그아웃 API
     * 리프레시 토큰을 삭제 및 엑세스 토큰 블랙리스트에 추가
     */
    @Operation(summary = "로그아웃")
    @DeleteMapping("/logout")
    public ResponseEntity<ApiResponseDto> logout(HttpServletRequest req, HttpServletResponse res) {
        ApiResponseDto apiResponseDto = userService.logout(req, res);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    // 토큰 재발급 API
    @Operation(summary = "액세스 토큰 재발급")
    @PostMapping("/refresh-token")
    public ResponseEntity<TokenResponseDto> requestRefresh(HttpServletResponse res, HttpServletRequest req) {
        TokenResponseDto tokenResponseDto = tokenService.requestRefreshToken(res, req);
        return new ResponseEntity<>(tokenResponseDto, HttpStatus.CREATED);
    }

    // Access Token 블랙리스트 확인 API -> 테스트용이라서 추후에 제거 (TokenRequestDto 도 삭제)
    private final RedisUtil redisUtil;

    @Operation(summary = "액세스 토큰 블랙리스트 확인")
    @PostMapping("/black-list")
    public ResponseEntity<Boolean> checkBlackList(@RequestBody TokenRequestDto token) {
        return new ResponseEntity<>(redisUtil.hasKeyBlackList(token.getToken()), HttpStatus.OK);
    }
}