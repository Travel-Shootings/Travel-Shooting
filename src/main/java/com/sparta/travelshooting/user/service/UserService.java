package com.sparta.travelshooting.user.service;

import com.sparta.travelshooting.user.dto.SignupRequestDto;

public interface UserService {

    /**
     * 회원가입 API
     * @param requestDto 회원가입 요청을 위한 데이터
     */
    void signup(SignupRequestDto requestDto);
}
