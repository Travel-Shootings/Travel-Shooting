package com.sparta.travelshooting.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor
@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 24) // 리프레시 토큰 저장 시간 : 24시간
public class RefreshToken {
    @Id
    private String refreshToken;

    private Long userId;

    public RefreshToken(Long id, String refreshTokenValue) {
        this.userId = id;
        this.refreshToken = refreshTokenValue;
    }

}
