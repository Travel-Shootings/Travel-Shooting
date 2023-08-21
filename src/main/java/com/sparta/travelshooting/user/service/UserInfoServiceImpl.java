package com.sparta.travelshooting.user.service;

import com.sparta.travelshooting.user.dto.EditInfoRequestDto;
import com.sparta.travelshooting.user.dto.UserResponseDto;
import com.sparta.travelshooting.user.entity.RegionEnum;
import com.sparta.travelshooting.user.entity.User;
import com.sparta.travelshooting.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService{

    private final UserRepository userRepository;

    // 유저 정보 조회 API
    @Override
    public UserResponseDto getUserInfo(User user) {
        User info = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("Not found"));

        return new UserResponseDto(info);
    }

    // 유저 정보 수정 API
    @Override
    @Transactional
    public UserResponseDto editUserInfo(User user, EditInfoRequestDto requestDto) {
        String nickname = requestDto.getNickname();
        RegionEnum region = RegionEnum.valueOf(requestDto.getRegion());

        User info = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("Not found"));
        info.update(nickname, region);

        return new UserResponseDto(info);
    }
}
