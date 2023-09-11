package com.sparta.travelshooting.reviewPost.repository;

import com.sparta.travelshooting.reviewPost.entity.ReviewPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewPostRepository extends JpaRepository<ReviewPost,Long> {

    //후기게시판 전체조회
    List<ReviewPost> findAll();



    List<ReviewPost> findAllByOrderByCreatedAtDesc();

    List<ReviewPost> findTop6ByOrderByCreatedAtDesc();
}
