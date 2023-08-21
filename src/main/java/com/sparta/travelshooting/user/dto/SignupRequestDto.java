package com.sparta.travelshooting.user.dto;

import com.sparta.travelshooting.user.entity.RegionEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignupRequestDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String username;

    @NotBlank
    private String nickname;

    @NotBlank
    private String region;
}
