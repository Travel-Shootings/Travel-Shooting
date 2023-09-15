package com.sparta.travelshooting.reviewPost.repository;

import com.sparta.travelshooting.reviewPost.dto.ReviewPostListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewPostRepositoryQuery {

    //후기 게시글 페이징 조회
    Page<ReviewPostListResponseDto> getPageReviewPosts(Pageable pageable);

}
