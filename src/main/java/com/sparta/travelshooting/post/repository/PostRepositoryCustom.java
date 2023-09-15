package com.sparta.travelshooting.post.repository;

import com.sparta.travelshooting.post.dto.PostListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PostRepositoryCustom {
    Page<PostListResponseDto> getPostsByPage(Pageable pageable);
}
