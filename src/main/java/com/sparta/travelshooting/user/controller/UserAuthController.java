package com.sparta.travelshooting.user.controller;

import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.redis.RedisUtil;
import com.sparta.travelshooting.user.dto.*;
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
    private final UserAuthService userAuthService;
    private final TokenService tokenService;
    private final RedisUtil redisUtil;

    // 회원가입 API
    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        ApiResponseDto apiResponseDto = userAuthService.signup(requestDto);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.CREATED);
    }

    // 로그인 API
    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse res) {
        ApiResponseDto apiResponseDto = userAuthService.login(requestDto, res);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    /**
     * 로그아웃 API
     * 리프레시 토큰을 삭제 및 엑세스 토큰 블랙리스트에 추가
     */
    @Operation(summary = "로그아웃")
    @DeleteMapping("/logout")
    public ResponseEntity<ApiResponseDto> logout(HttpServletRequest req, HttpServletResponse res) {
        ApiResponseDto apiResponseDto = userAuthService.logout(req, res);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    // 토큰 재발급 API
    @Operation(summary = "액세스 토큰 재발급")
    @PostMapping("/refresh-token")
    public ResponseEntity<TokenResponseDto> requestRefresh(HttpServletResponse res, HttpServletRequest req) {
        TokenResponseDto tokenResponseDto = tokenService.requestRefreshToken(res, req);
        return new ResponseEntity<>(tokenResponseDto, HttpStatus.CREATED);
    }

    // email 인증
    @Operation(summary = "email 인증")
    @PostMapping("/mail")
    public ResponseEntity<ApiResponseDto> sendMail(@RequestBody MailRequestDto mailRequestDto) {
        ApiResponseDto mailResponseDto = userAuthService.sendMail(mailRequestDto.getEmail());
        return new ResponseEntity<>(mailResponseDto, HttpStatus.OK);
    }

    // email 인증번호 확인
    @Operation(summary = "인증번호 확인")
    @PostMapping("/mail/confirm")
    public ResponseEntity<ApiResponseDto> confirmAuthNumber(@RequestBody AuthNumberRequestDto authNumberRequestDto) {
        ApiResponseDto mailResponseDto = userAuthService.confirmAuthNumber(authNumberRequestDto.getAuthNumber());
        return new ResponseEntity<>(mailResponseDto, HttpStatus.OK);
    }

    // Access Token 블랙리스트 확인 API -> 테스트용이라서 추후에 제거 (TokenRequestDto 도 삭제)

    @Operation(summary = "액세스 토큰 블랙리스트 확인")
    @PostMapping("/black-list")
    public ResponseEntity<Boolean> checkBlackList(@RequestBody TokenRequestDto token) {
        return new ResponseEntity<>(redisUtil.hasKeyBlackList(token.getToken()), HttpStatus.OK);
    }
}