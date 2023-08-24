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
    public ResponseEntity<ApiResponseDto> signup(@RequestBody SignupRequestDto requestDto) {
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
     * - 로그아웃 -> 리프레시 토큰을 삭제
     * - 남아있는 AccessToken은 어떻게 처리하는 거지..? -> 블랙리스트에 저장
     * TODO : 만료된 AccessToken을 정기적으로 삭제하는 것 -> redis로 업데이트
     */
    @Operation(summary = "로그아웃")
    @DeleteMapping("/logout")
    public ResponseEntity<ApiResponseDto> logout(HttpServletRequest req, HttpServletResponse res) {
        ApiResponseDto apiResponseDto = userService.logout(req, res);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    // 토큰 재발급 API
    // 자동으로 호출되는 것은 아마 js로 처리해야 하는 것 같음
    @Operation(summary = "액세스 토큰 재발급")
    @PostMapping("/refresh-token")
    public ResponseEntity<TokenResponseDto> requestRefresh(HttpServletResponse res, HttpServletRequest req) {
        TokenResponseDto tokenResponseDto = tokenService.requestRefreshToken(res, req);
        return new ResponseEntity<>(tokenResponseDto, HttpStatus.CREATED);
    }

    // IllegalArgumentException 에 대한 예외처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDto> illegalArgumentException(IllegalArgumentException e) {
        ApiResponseDto apiResponseDto = new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.BAD_REQUEST);
    }

    // Access Token 블랙리스트 확인 API -> 테스트용이라서 추후에 제거 (TokenRequestDto 도 삭제)
    private final RedisUtil redisUtil;

    @Operation(summary = "액세스 토큰 블랙리스트 확인")
    @PostMapping("/black-list")
    public ResponseEntity<Boolean> checkBlackList(@RequestBody TokenRequestDto token) {
        return new ResponseEntity<>(redisUtil.hasKeyBlackList(token.getToken()), HttpStatus.OK);
    }
}