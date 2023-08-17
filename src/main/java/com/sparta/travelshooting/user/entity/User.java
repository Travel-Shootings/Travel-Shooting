package com.sparta.travelshooting.user.entity;

import com.sparta.travelshooting.user.dto.SignupRequestDto;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String nickname;

    @Enumerated(value = EnumType.STRING)
    private RegionEnum region;

    public User(SignupRequestDto requestDto, String password, RegionEnum region) {
        this.email = requestDto.getEmail();
        this.password = password;
        this.username = requestDto.getUsername();
        this.nickname = requestDto.getNickname();
        this.region = region;
    }
}
