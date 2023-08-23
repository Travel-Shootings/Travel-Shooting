package com.sparta.travelshooting.post.repository;

import com.sparta.travelshooting.post.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository <PostLike, Long> {
    Optional<PostLike> findByPostIdAndUserId(Long postId, Long userId);
}
