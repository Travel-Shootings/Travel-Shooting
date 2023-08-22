package com.sparta.travelshooting.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class TokenBlackList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String accessToken;

    @Column
    private Date expirationDate;

    public TokenBlackList(String accessToken, Date expireationDate) {
        this.accessToken = accessToken;
        this.expirationDate = expireationDate;
    }
}
