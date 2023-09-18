package com.sparta.travelshooting.reviewPost.entity;

import com.sparta.travelshooting.common.Timestamped;
import com.sparta.travelshooting.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@RequiredArgsConstructor
public class ReviewPostLike extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_post_id")
    private ReviewPost reviewPost;

    public ReviewPostLike(User user, ReviewPost reviewPost) {
        this.user = user;
        this.reviewPost = reviewPost;
    }
}