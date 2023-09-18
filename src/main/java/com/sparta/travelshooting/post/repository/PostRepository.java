package com.sparta.travelshooting.post.repository;

import com.sparta.travelshooting.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();

    List<Post> findTop3ByOrderByCreatedAtDesc();
}