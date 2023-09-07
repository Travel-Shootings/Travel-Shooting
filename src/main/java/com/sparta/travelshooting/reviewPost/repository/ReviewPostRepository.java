package com.sparta.travelshooting.reviewPost.repository;

import com.sparta.travelshooting.post.entity.Post;
import com.sparta.travelshooting.reviewPost.entity.ReviewPost;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewPostRepository extends JpaRepository<ReviewPost,Long> {

    //후기게시판 전체조회
    List<ReviewPost> findAll();



    List<ReviewPost> findAllByOrderByCreatedAtDesc();

}
