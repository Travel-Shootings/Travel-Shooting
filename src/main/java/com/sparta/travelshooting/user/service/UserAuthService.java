package com.sparta.travelshooting.user.service;

import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.user.dto.LoginRequestDto;
import com.sparta.travelshooting.user.dto.SignupRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserAuthService {

    /**
     * 회원가입 API
     * @param requestDto 회원가입 요청을 위한 데이터
     */
    ApiResponseDto signup(SignupRequestDto requestDto);

    /**
     * 로그인 API
     * @param requestDto 로그인 요청을 위한 데이터
     * @param res 쿠키를 생성하기 위한 response
     */
    ApiResponseDto login(LoginRequestDto requestDto, HttpServletResponse res);

    /**
     * 로그아웃 API
     * @param req 로그아웃 요청 데이터
     */
    ApiResponseDto logout(HttpServletRequest req, HttpServletResponse res);
}
