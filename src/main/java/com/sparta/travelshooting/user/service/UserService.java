package com.sparta.travelshooting.user.service;

import com.sparta.travelshooting.user.dto.LoginRequestDto;
import com.sparta.travelshooting.user.dto.SignupRequestDto;
import com.sparta.travelshooting.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

    /**
     * 회원가입 API
     * @param requestDto 회원가입 요청을 위한 데이터
     */
    void signup(SignupRequestDto requestDto);

    /**
     * 로그인 API
     * @param requestDto 로그인 요청을 위한 데이터
     * @param res 쿠키를 생성하기 위한 response
     */
    void login(LoginRequestDto requestDto, HttpServletResponse res);

    /**
     * 로그아웃 API
     * @param user 로그아웃 요청을 위한 유저 데이터
     * @param res
     */
    void logout(User user, HttpServletRequest req, HttpServletResponse res);
}
