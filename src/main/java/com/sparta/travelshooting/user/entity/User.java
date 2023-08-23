package com.sparta.travelshooting.user.entity;

import com.sparta.travelshooting.post.entity.PostLike;
import com.sparta.travelshooting.user.dto.SignupRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "users")
@Getter
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

    @Enumerated(value = EnumType.STRING)
    private RoleEnum role;

    @Column
    private String recentPassword;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<PostLike> postLikes = new ArrayList<>();

    public User(SignupRequestDto requestDto, String password, RegionEnum region, RoleEnum role) {
        this.email = requestDto.getEmail();
        this.password = password;
        this.username = requestDto.getUsername();
        this.nickname = requestDto.getNickname();
        this.region = region;
        this.role = role;
    }

    public void update(String nickname, RegionEnum region) {
        this.nickname = nickname;
        this.region = region;
    }

    public void passwordUpdate(String newPassword) {
        this.recentPassword = this.password;
        this.password = newPassword;
    }
}
