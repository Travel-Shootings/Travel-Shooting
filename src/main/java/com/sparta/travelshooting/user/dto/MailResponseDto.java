package com.sparta.travelshooting.user.dto;

import lombok.Getter;

@Getter
public class MailResponseDto {
    private String message;
    private Integer authNumber;
    private Integer statusCode;


    public MailResponseDto(String message, int authNumber, int value) {
        this.message = message;
        this.authNumber = authNumber;
        this.statusCode = value;
    }
}
