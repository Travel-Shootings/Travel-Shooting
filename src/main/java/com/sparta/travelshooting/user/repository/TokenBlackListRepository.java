package com.sparta.travelshooting.user.repository;

import com.sparta.travelshooting.user.entity.TokenBlackList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenBlackListRepository extends JpaRepository<TokenBlackList, Long> {
    Optional<TokenBlackList> findByAccessToken(String tokenValue);
}
