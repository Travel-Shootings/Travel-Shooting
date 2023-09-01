package com.sparta.travelshooting.admin.dto;

import com.sparta.travelshooting.user.entity.RegionEnum;
import com.sparta.travelshooting.user.entity.RoleEnum;
import com.sparta.travelshooting.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AdminProfileRequestDto {

    private String nickname;

    @NotBlank
    private RoleEnum role;
}
