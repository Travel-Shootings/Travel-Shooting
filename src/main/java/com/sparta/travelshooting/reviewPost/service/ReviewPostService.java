package com.sparta.travelshooting.reviewPost.service;

import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.reviewPost.dto.ReviewPostRequestDto;
import com.sparta.travelshooting.reviewPost.dto.ReviewPostResponseDto;
import com.sparta.travelshooting.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewPostService {
    //후기 게시글 작성
    ApiResponseDto createReviewPost(List<MultipartFile> imageFiles, ReviewPostRequestDto requestDto, User user);

    //후기 게시글 수정
    ApiResponseDto updateReviewPost(Long ReviewPostId, List<MultipartFile> imageFiles, ReviewPostRequestDto requestDto, User user);

    //후기 게시글 삭제
    ApiResponseDto deleteReviewPost(Long reviewPostId, User user);

    //후기 게시글 단건 조회
    ReviewPostResponseDto getReviewPost(Long reviewPostId);

    //후기 게시글 전체 조회
    List<ReviewPostResponseDto> getAllReviewPosts();

    //후기 게시글 좋아요
    ApiResponseDto addLike(Long reviewPostId, User user);

    //후기 게시글 좋아요 취소
    ApiResponseDto deleteLike(Long ReviewPostId, User user);

    //좋아요 여부 조회
    boolean hasLiked(Long reviewPostId, Long userId);

}
