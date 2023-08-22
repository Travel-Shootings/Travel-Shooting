package com.sparta.travelshooting.user.service;

import com.sparta.travelshooting.user.dto.EditInfoRequestDto;
import com.sparta.travelshooting.user.dto.PasswordRequestDto;
import com.sparta.travelshooting.user.dto.UserResponseDto;
import com.sparta.travelshooting.user.entity.User;

public interface UserInfoService {

    /**
     * 유저 정보 조회 API
     * @param user 로그인 된 유저
     * @return 유저의 정보가 담긴 데이터 반환
     */
    UserResponseDto getUserInfo(User user);

    /**
     * 유저 정보 수정 API
     * @param user 로그인 된 유저
     * @param requestDto 수정 데이터
     * @return 수정된 유저의 정보가 담긴 데이터 반환
     */
    UserResponseDto editUserInfo(User user, EditInfoRequestDto requestDto);


    /**
     * 유저 비밀번호 수정 API
     * @param user 로그인 된 유저
     * @param requestDto 비밀번호 수정 데이터
     */
    void editUserPassword(User user, PasswordRequestDto requestDto);
}
