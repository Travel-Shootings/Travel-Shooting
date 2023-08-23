package com.sparta.travelshooting.admin.dto;

import com.sparta.travelshooting.user.entity.RegionEnum;
import com.sparta.travelshooting.user.entity.RoleEnum;
import com.sparta.travelshooting.user.entity.User;
import lombok.Getter;

@Getter
public class AdminProfileRequestDto {
    private String email;
    private String password;
    private String nickname;
    private RegionEnum region;
    private RoleEnum role;
}
