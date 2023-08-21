package com.sparta.travelshooting.user.service;

import com.sparta.travelshooting.user.dto.LoginRequestDto;
import com.sparta.travelshooting.user.dto.SignupRequestDto;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

    /**
     * 회원가입 API
     * @param requestDto 회원가입 요청을 위한 데이터
     */
    void signup(SignupRequestDto requestDto);

    /**
     * 로그인 API
     *
     * @param requestDto 로그인 요청을 위한 데이터
     * @param res 쿠키를 생성하기 위한 response
     */
    void login(LoginRequestDto requestDto, HttpServletResponse res);
}
