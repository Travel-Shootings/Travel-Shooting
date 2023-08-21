package com.sparta.travelshooting.post.repository;

import com.sparta.travelshooting.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
