package com.sparta.travelshooting.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long userId;

    @Column
    private String tokenValue;

    @Column
    private String accessToken;

    public RefreshToken(Long id, String refreshTokenValue, String token) {
        this.userId = id;
        this.tokenValue = refreshTokenValue;
        this.accessToken = token;
    }

    public void update(String token) {
        this.accessToken = token;
    }
}
