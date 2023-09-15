package com.sparta.travelshooting.user.dto;

import lombok.Getter;

@Getter
public class TokenResponseDto {

    private String AccessToken;

    private String RefreshToken;

    public TokenResponseDto(String tokenValue, String token) {
        this.AccessToken = token;
        this.RefreshToken = tokenValue;
    }
}
