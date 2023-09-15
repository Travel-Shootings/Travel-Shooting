package com.sparta.travelshooting.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtil {
    private final RedisTemplate<String, Object> redisBlackListTemplate;
    private final RedisTemplate<String, Object> redisAuthNumberTemplate;

    // 블랙리스트에 저장
    public void setBlackList(String key, Object o, Long milliSeconds) {
        redisBlackListTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(o.getClass()));
        redisBlackListTemplate.opsForValue().set(key, o, milliSeconds, TimeUnit.MILLISECONDS);
    }

    // 블랙리스트에 해당 키가 있는지 조회
    public boolean hasKeyBlackList(String key) {
        return Boolean.TRUE.equals(redisBlackListTemplate.hasKey(key));
    }

    // email 인증번호 저장
    public void setAuthNumber(Integer key, Object o, Long milliSeconds) {
        redisAuthNumberTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(o.getClass()));
        redisAuthNumberTemplate.opsForValue().set(String.valueOf(key), o, milliSeconds, TimeUnit.MILLISECONDS);
    }

    // email 인증번호 조회
    public boolean hasAuthNumber(String key) {
        return Boolean.TRUE.equals(redisAuthNumberTemplate.hasKey(key));
    }

}
