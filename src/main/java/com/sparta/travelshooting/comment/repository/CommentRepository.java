package com.sparta.travelshooting.comment.repository;

import com.sparta.travelshooting.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
