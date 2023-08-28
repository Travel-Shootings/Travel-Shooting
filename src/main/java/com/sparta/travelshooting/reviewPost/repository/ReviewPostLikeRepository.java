package com.sparta.travelshooting.reviewPost.repository;

import com.sparta.travelshooting.reviewPost.entity.ReviewPostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewPostLikeRepository  extends JpaRepository<ReviewPostLike, Long> {

    Optional<ReviewPostLike> findByReviewPostIdAndUserId(Long reviewPostId, Long userId);

}
