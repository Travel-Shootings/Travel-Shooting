package com.sparta.travelshooting.user.service;

import com.sparta.travelshooting.user.dto.UserResponseDto;
import com.sparta.travelshooting.user.entity.User;
import com.sparta.travelshooting.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService{

    private final UserRepository userRepository;

    @Override
    public UserResponseDto getUserInfo(User user) {
        User info = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("Not found"));

        return new UserResponseDto(info);
    }
}
