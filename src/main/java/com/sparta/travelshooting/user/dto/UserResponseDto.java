package com.sparta.travelshooting.user.dto;

import com.sparta.travelshooting.user.entity.RegionEnum;
import com.sparta.travelshooting.user.entity.RoleEnum;
import com.sparta.travelshooting.user.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private String email;

    private String nickname;

    private String username;

    private RegionEnum region;

    private RoleEnum role;

    public UserResponseDto(User info) {
        this.email = info.getEmail();
        this.nickname = info.getNickname();
        this.username = info.getUsername();
        this.region = info.getRegion();
        this.role = info.getRole();
    }
}
