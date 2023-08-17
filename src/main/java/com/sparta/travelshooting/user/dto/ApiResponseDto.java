package com.sparta.travelshooting.user.dto;

import lombok.Getter;

@Getter
public class ApiResponseDto {
    private String msg;
    private int status;

    public ApiResponseDto(String msg, int status) {
        this.msg = msg;
        this.status = status;
    }
}
