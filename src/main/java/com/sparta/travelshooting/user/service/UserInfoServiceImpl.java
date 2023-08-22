package com.sparta.travelshooting.user.service;

import com.sparta.travelshooting.user.dto.EditInfoRequestDto;
import com.sparta.travelshooting.user.dto.PasswordRequestDto;
import com.sparta.travelshooting.user.dto.UserResponseDto;
import com.sparta.travelshooting.user.entity.RegionEnum;
import com.sparta.travelshooting.user.entity.User;
import com.sparta.travelshooting.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 유저 정보 조회 API
    @Override
    public UserResponseDto getUserInfo(User user) {
        User info = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("Not found User"));
        return new UserResponseDto(info);
    }

    // 유저 정보 수정 API
    @Override
    @Transactional
    public UserResponseDto editUserInfo(User user, EditInfoRequestDto requestDto) {
        String nickname = requestDto.getNickname();
        RegionEnum region = RegionEnum.valueOf(requestDto.getRegion());

        User info = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("Not found User"));
        info.update(nickname, region);

        return new UserResponseDto(info);
    }

    // 유저 비밀번호 수정 API
    @Override
    @Transactional
    public void editUserPassword(User user, PasswordRequestDto requestDto) {
        User info = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("Not found"));

        // 기존 비밀번호 매치
        if (!passwordEncoder.matches(requestDto.getOldPassword(), info.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 가장 최근 비밀번호와 매치
        if (passwordEncoder.matches(requestDto.getNewPassword(), info.getRecentPassword())) {
            throw new IllegalArgumentException("최근에 사용했던 비밀번호입니다.");
        }

        String newPassword = passwordEncoder.encode(requestDto.getNewPassword());
        info.passwordUpdate(newPassword);
    }
}
