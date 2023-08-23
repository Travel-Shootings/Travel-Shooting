package com.sparta.travelshooting.reviewPost.service;

import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.reviewPost.dto.ReviewPostRequestDto;
import com.sparta.travelshooting.reviewPost.dto.ReviewPostResponseDto;
import com.sparta.travelshooting.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface ReviewPostService {
    //후기 게시글 작성
    ReviewPostResponseDto createReviewPost(MultipartFile imageFile, ReviewPostRequestDto requestDto, User user);

    //후기 게시글 수정
    ApiResponseDto updateReviewPost(Long ReviewPostId, MultipartFile imageFile, ReviewPostRequestDto requestDto, User user);


    //후기 게시글 삭제
    ApiResponseDto deleteReviewPost(Long reviewPostId, User user);

}
