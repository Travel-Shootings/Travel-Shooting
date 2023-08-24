package com.sparta.travelshooting.reviewPost.repository;

import com.sparta.travelshooting.post.entity.Post;
import com.sparta.travelshooting.reviewPost.entity.ReviewPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewPostRepository extends JpaRepository<ReviewPost,Long> {

    List<ReviewPost> findAll();
}
