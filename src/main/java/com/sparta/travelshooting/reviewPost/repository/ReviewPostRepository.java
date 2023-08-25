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

    @EntityGraph(attributePaths = "images")
    Optional<ReviewPost> findById(Long id); // 특정 ID의 게시물과 이미지 조회
}
