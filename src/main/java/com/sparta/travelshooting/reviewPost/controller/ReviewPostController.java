package com.sparta.travelshooting.reviewPost.controller;

import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.reviewPost.dto.ReviewPostListResponseDto;
import com.sparta.travelshooting.reviewPost.dto.ReviewPostRequestDto;
import com.sparta.travelshooting.reviewPost.dto.ReviewPostResponseDto;
import com.sparta.travelshooting.reviewPost.entity.ReviewPost;
import com.sparta.travelshooting.reviewPost.service.ReviewPostService;
import com.sparta.travelshooting.security.UserDetailsImpl;
import com.sparta.travelshooting.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/review-posts")
@Slf4j
@Tag(name = "여행 계획 후기 게시글 API")
public class ReviewPostController {

    private final ReviewPostService reviewPostService;

    //후기 게시글 생성
    @Operation(summary = "후기 게시글 작성")
    @PostMapping
    public ResponseEntity<ApiResponseDto> createReviewPost(
            @RequestParam(value = "images", required = false) List<MultipartFile> imageFiles,
            @ModelAttribute ReviewPostRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto apiResponseDto = reviewPostService.createReviewPost(imageFiles, requestDto, userDetails.getUser());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.CREATED);
    }

    // 후기 게시글 수정
    @Operation(summary = "후기 게시글 수정")
    @PutMapping("/{reviewPostId}")
    public ResponseEntity<ApiResponseDto> updateReviewPost(
            @PathVariable Long reviewPostId,
            @RequestParam(value = "images", required = false) List<MultipartFile> imageFiles,
            @ModelAttribute ReviewPostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        ApiResponseDto apiResponseDto = reviewPostService.updateReviewPost(reviewPostId, imageFiles, requestDto, userDetails.getUser());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }


    // 후기 게시글 삭제
    @Operation(summary = "후기 게시글 삭제")
    @DeleteMapping("/{reviewPostId}")
    public ResponseEntity<ApiResponseDto> deleteReviewPost(@PathVariable Long reviewPostId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 로그인이 되어있지 않은 경우 처리
        if (userDetails == null) {
            return new ResponseEntity<>(new ApiResponseDto("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED.value()), HttpStatus.OK);
        }

        ApiResponseDto apiResponseDto = reviewPostService.deleteReviewPost(reviewPostId, userDetails.getUser());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    // 후기 게시글 단건 조회
    @GetMapping("/{reviewPostId}")
    public ResponseEntity<ReviewPostResponseDto> getReviewPost(@PathVariable Long reviewPostId) {
        ReviewPostResponseDto responseDto = reviewPostService.getReviewPost(reviewPostId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 후기 게시글 전체 조회
    @GetMapping
    public ResponseEntity<List<ReviewPostListResponseDto>> getAllReviewPosts() {
        List<ReviewPostListResponseDto> responseDto = reviewPostService.getAllReviewPosts();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 후기 게시글을 페이징하여 조회
    @GetMapping("/page")
    public ResponseEntity<Page<ReviewPostListResponseDto>> getPageReviewPosts(@PageableDefault(size = 6) Pageable pageable) {
        Page<ReviewPostListResponseDto> responseDto = reviewPostService.getPageReviewPosts(pageable);
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

    //좋아요 여부 조회
    @GetMapping("/like/{reviewPostId}")
    public ResponseEntity<ApiResponseDto> checkLikeStatus(@PathVariable Long reviewPostId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 로그인이 되어있지 않은 경우 처리
        if (userDetails == null) {
            return new ResponseEntity<>(new ApiResponseDto("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED.value()), HttpStatus.OK);
        }

        Long userId = userDetails.getUser().getId();
        boolean isLiked = reviewPostService.hasLiked(reviewPostId, userId);
        if (isLiked) {
            return new ResponseEntity<>(new ApiResponseDto("이미 좋아요를 누른 상태입니다.", 200), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponseDto("아직 좋아요를 누르지 않은 상태입니다.", 200), HttpStatus.OK);
        }
    }


    //작성자 확인
    @GetMapping("/check-user/{reviewPostId}")
    public ResponseEntity<Boolean> reviewPostCheckUser(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long reviewPostId) {
        //로그인 되어있지 않은 경우 처리
        if (userDetails == null) {
            return ResponseEntity.ok(false);
        }

        boolean isAuthor = reviewPostService.reviewPostCheckUser(userDetails, reviewPostId);
        return ResponseEntity.ok(isAuthor);
    }
}