package com.sparta.travelshooting.admin.dto;

import com.sparta.travelshooting.user.entity.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AdminProfileRequestDto {

    private String nickname;

    @NotBlank
    private RoleEnum role;
}
