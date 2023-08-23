package com.sparta.travelshooting.post.repository;

import com.sparta.travelshooting.post.entity.Post;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepositoryCustom {
    List<Post> getPostListWithId(long offset, Long id);
}
