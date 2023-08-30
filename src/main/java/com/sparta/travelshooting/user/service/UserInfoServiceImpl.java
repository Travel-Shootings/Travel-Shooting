package com.sparta.travelshooting.user.service;

import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.user.dto.EditInfoRequestDto;
import com.sparta.travelshooting.user.dto.PasswordRequestDto;
import com.sparta.travelshooting.user.dto.UserResponseDto;
import com.sparta.travelshooting.user.entity.RegionEnum;
import com.sparta.travelshooting.user.entity.User;
import com.sparta.travelshooting.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    // 유저 정보 조회 API
    @Override
    public UserResponseDto getUserInfo(User user) {
        return new UserResponseDto(user);
    }

    // 유저 정보 수정 API
    @Override
    @Transactional
    public ApiResponseDto editUserInfo(User user, EditInfoRequestDto requestDto) {
        String nickname = requestDto.getNickname();
        RegionEnum region = RegionEnum.valueOf(requestDto.getRegion());
        user.update(nickname, region);

        userRepository.save(user);
        return new ApiResponseDto("프로필 수정이 완료되었습니다.", HttpStatus.OK.value());
    }

    // 유저 비밀번호 수정 API
    @Override
    @Transactional
    public ApiResponseDto editUserPassword(User user, PasswordRequestDto requestDto) {
        // 기존 비밀번호 매치
        if (!passwordEncoder.matches(requestDto.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 가장 최근 비밀번호와 매치
        if (passwordEncoder.matches(requestDto.getNewPassword(), user.getRecentPassword()) || passwordEncoder.matches(requestDto.getNewPassword(), user.getPassword())) {
            throw new IllegalArgumentException("최근에 사용한 비밀번호입니다.");
        }

        String newPassword = passwordEncoder.encode(requestDto.getNewPassword());
        user.passwordUpdate(newPassword);
        userRepository.save(user);

        return new ApiResponseDto("비밀번호를 수정했습니다.", HttpStatus.OK.value());
    }
}
