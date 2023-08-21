package com.sparta.travelshooting.user.controller;

import com.sparta.travelshooting.jwt.JwtUtil;
import com.sparta.travelshooting.security.UserDetailsImpl;
import com.sparta.travelshooting.user.dto.ApiResponseDto;
import com.sparta.travelshooting.user.dto.LoginRequestDto;
import com.sparta.travelshooting.user.dto.SignupRequestDto;
import com.sparta.travelshooting.user.dto.TokenResponseDto;
import com.sparta.travelshooting.user.service.TokenService;
import com.sparta.travelshooting.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TokenService tokenService;
    private final JwtUtil jwtUtil;

    // 회원가입 API
    @PostMapping("/user/signup")
    public ResponseEntity<ApiResponseDto> signup(@RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);

        ApiResponseDto apiResponseDto = new ApiResponseDto("회원가입이 되었습니다.", HttpStatus.CREATED.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.CREATED);
    }

    // 로그인 API
    @PostMapping("/user/login")
    public ResponseEntity<ApiResponseDto> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse res) {
        userService.login(requestDto, res);

        ApiResponseDto apiResponseDto = new ApiResponseDto("로그인이 완료되었습니다.", HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    // 토큰 재발급 API
    @PostMapping("/user/refresh-token")
    public ResponseEntity<TokenResponseDto> requestRefresh(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse res, HttpServletRequest req) {
        // 현재 쿠키 삭제
        jwtUtil.deleteCookie(jwtUtil.getTokenFromRequest(req), res);

        TokenResponseDto tokenResponseDto = tokenService.requestRefreshToken(userDetails.getUser(), res);
        return new ResponseEntity<>(tokenResponseDto, HttpStatus.CREATED);
    }

    /**
     * 로그아웃 API
     * - 해당 쿠키에서 토큰을 추출해서 블랙리스트에 추가
     * - redis를 활용하면 성능이 더 좋지만 우선 DB에 저장 (추후에 업데이트)
     * - 로그아웃을 하면 리프레시 토큰을 삭제해야한다.
     * - 남아있는 AccessToken은 어떻게 처리하는 거지..? -> 블랙리스트
      */
    @DeleteMapping("/user/logout")
    public ResponseEntity<ApiResponseDto> logout(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletRequest req, HttpServletResponse res) {
        userService.logout(userDetails.getUser(), req, res);

        ApiResponseDto apiResponseDto = new ApiResponseDto("로그아웃 성공", HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    // IllegalArgumentException 에 대한 예외처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDto> illegalArgumentException(IllegalArgumentException e) {
        ApiResponseDto apiResponseDto = new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.BAD_REQUEST);
    }
}
