package com.sparta.travelshooting.reviewPost.controller;

import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.reviewPost.dto.ReviewPostRequestDto;
import com.sparta.travelshooting.reviewPost.dto.ReviewPostResponseDto;
import com.sparta.travelshooting.reviewPost.service.ReviewPostService;
import com.sparta.travelshooting.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/reviewPosts")
@Tag(name = "여행 계획 후기 게시글 API")
public class ReviewPostController {

    private final ReviewPostService reviewPostService;

    //후기 게시글 생성
    @Operation(summary = "후기 게시글 작성")
    @PostMapping("")
    public ResponseEntity<ApiResponseDto> createReviewPost(
            @RequestParam(value = "images", required = false) List<MultipartFile> imageFiles,
            @ModelAttribute ReviewPostRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        ReviewPostResponseDto responseDto = reviewPostService.createReviewPost(imageFiles, requestDto, userDetails.getUser());
        ApiResponseDto apiResponseDto = new ApiResponseDto("게시글이 생성되었습니다.", HttpStatus.CREATED.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.CREATED);
    }

    // 후기 게시글 수정
    @Operation(summary = "후기 게시글 수정")
    @PatchMapping("/{reviewPostId}")
    public ResponseEntity<ApiResponseDto> updateReviewPost(@PathVariable Long reviewPostId, @RequestParam(value = "images", required = false) List<MultipartFile> imageFiles, @ModelAttribute ReviewPostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            ApiResponseDto apiResponseDto = reviewPostService.updateReviewPost(reviewPostId, imageFiles, requestDto, userDetails.getUser());
            return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
        } catch (RejectedExecutionException e) {
            ApiResponseDto apiResponseDto = new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(apiResponseDto, HttpStatus.BAD_REQUEST);
        }
    }


    // 후기 게시글 삭제
    @Operation(summary = "후기 게시글 삭제")
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

    // 후기 게시글 단건 조회
    @GetMapping("/{reviewPostId}")
    public ResponseEntity<ReviewPostResponseDto> getReviewPost(@PathVariable Long reviewPostId) {
        try {
            ReviewPostResponseDto responseDto = reviewPostService.getReviewPost(reviewPostId);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 후기 게시글 전체 조회
    @GetMapping("")
    public ResponseEntity<List<ReviewPostResponseDto>> getAllReviewPosts() {
        List<ReviewPostResponseDto> responseDto = reviewPostService.getAllReviewPosts();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    //좋아요 기능
    @PostMapping("/like/{reviewPostId}")
    public ResponseEntity<ApiResponseDto> addLike(@PathVariable Long reviewPostId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto apiResponseDto = reviewPostService.addLike(reviewPostId, userDetails.getUser());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    //좋아요 취소 기능
    @DeleteMapping("/like/{reviewPostId}")
    public ResponseEntity<ApiResponseDto> deleteLike(@PathVariable Long reviewPostId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto apiResponseDto = reviewPostService.deleteLike(reviewPostId, userDetails.getUser());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }


}
