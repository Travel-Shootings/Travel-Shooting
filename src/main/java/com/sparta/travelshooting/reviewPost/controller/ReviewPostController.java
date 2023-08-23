package com.sparta.travelshooting.reviewPost.controller;

import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.reviewPost.dto.ReviewPostRequestDto;
import com.sparta.travelshooting.reviewPost.dto.ReviewPostResponseDto;
import com.sparta.travelshooting.reviewPost.service.ReviewPostService;
import com.sparta.travelshooting.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.RejectedExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviewPosts")
public class ReviewPostController {

    private final ReviewPostService reviewPostService;

//    후기 게시글 작성
// 후기 게시글 작성
@PostMapping("")
public ResponseEntity<ApiResponseDto> createReviewPost(@RequestParam("images") MultipartFile imageFile, @ModelAttribute ReviewPostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
    try {
        ReviewPostResponseDto responseDto = reviewPostService.createReviewPost(imageFile, requestDto, userDetails.getUser());
        ApiResponseDto apiResponseDto = new ApiResponseDto("게시글이 생성되었습니다.", HttpStatus.CREATED.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.CREATED);
    } catch (RejectedExecutionException e) {
        ApiResponseDto apiResponseDto = new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.BAD_REQUEST);
    }
}



    // 후기 게시글 수정
    @PutMapping("/{reviewPostId}")
    public ResponseEntity<ApiResponseDto> updateReviewPost(@PathVariable Long reviewPostId, @RequestParam(required = false) MultipartFile imageFile, @ModelAttribute ReviewPostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            ApiResponseDto apiResponseDto = reviewPostService.updateReviewPost(reviewPostId, imageFile, requestDto, userDetails.getUser());
            return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
        } catch (RejectedExecutionException e) {
            ApiResponseDto apiResponseDto = new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(apiResponseDto, HttpStatus.BAD_REQUEST);
        }
    }


    // 후기 게시글 삭제
    @DeleteMapping("/{reviewPostId}")
    public ResponseEntity<ApiResponseDto> deleteReviewPost(@PathVariable Long reviewPostId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            ApiResponseDto apiResponseDto = reviewPostService.deleteReviewPost(reviewPostId, userDetails.getUser());
            return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
        } catch (RejectedExecutionException e) {
            ApiResponseDto apiResponseDto = new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(apiResponseDto, HttpStatus.BAD_REQUEST);
        }
    }




}
