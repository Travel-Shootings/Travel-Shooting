package com.sparta.travelshooting.user.dto;

import lombok.Getter;

@Getter
public class PasswordRequestDto {
    private String oldPassword;
    private String newPassword;
}
