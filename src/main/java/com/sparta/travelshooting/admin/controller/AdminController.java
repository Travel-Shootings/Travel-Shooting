package com.sparta.travelshooting.admin.controller;

import com.sparta.travelshooting.admin.dto.AdminCommentRequestDto;
import com.sparta.travelshooting.admin.dto.AdminProfileRequestDto;
import com.sparta.travelshooting.admin.dto.AdminSummaryResponseDto;
import com.sparta.travelshooting.admin.service.AdminService;
import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.journeylist.dto.JourneyListRequestDto;
import com.sparta.travelshooting.post.dto.PostRequestDto;
import com.sparta.travelshooting.post.dto.PostResponseDto;
import com.sparta.travelshooting.reply.dto.ReplyRequestDto;
import com.sparta.travelshooting.reviewPost.dto.ReviewPostRequestDto;
import com.sparta.travelshooting.reviewPost.dto.ReviewPostResponseDto;
import com.sparta.travelshooting.user.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


//@Secured("ADMIN")
@RequiredArgsConstructor
@Controller
@RequestMapping("/api/admin")
@Tag(name = "관리자용 API")
public class AdminController {

    private final AdminService adminService;

    // 총 유저, 게시글, 댓글 수
    @GetMapping("/summary")
    public ResponseEntity<AdminSummaryResponseDto> showSummary() {
        AdminSummaryResponseDto adminSummaryResponseDto = adminService.showSummary();
        return new ResponseEntity<>(adminSummaryResponseDto, HttpStatus.OK);
    }

    // 유저 전체 정보 조회
    // TODO : 페이징 처리
    @Operation(summary = "유저 전체 정보 조회")
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> showUsers() {
        List<UserResponseDto> userResponseDto = adminService.showUsers();
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    // 유저 정보 조회
    @Operation(summary = "유저 정보 조회")
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponseDto> showUser(@PathVariable Long userId) {
        UserResponseDto userResponseDto = adminService.showUser(userId);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    // 글 전체 정보 조회
    // TODO : 페이징 처리
    @Operation(summary = "글 전체 정보 조회")
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>> showPosts() {
        List<PostResponseDto> postResponseDto = adminService.showPosts();
        return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
    }

    // 후기 게시글 정보 조회
    @Operation(summary = "후기 게시글 전체 정보 조회")
    @GetMapping("/review-posts")
    public ResponseEntity<List<ReviewPostResponseDto>> showReviewPosts() {
        List<ReviewPostResponseDto> reviewPostResponseDto = adminService.showReviewPosts();
        return new ResponseEntity<>(reviewPostResponseDto, HttpStatus.OK);
    }

    //유저 프로필 수정
    // 유저 닉네임, 권한
    @Operation(summary = "유저 프로필 수정")
    @PutMapping("/user/{userId}")
    public ResponseEntity<ApiResponseDto> updateUser(@PathVariable Long userId, @RequestBody AdminProfileRequestDto requestDto) {
        ApiResponseDto apiResponseDto = adminService.updateUser(userId, requestDto);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    // 유저 삭제
    @Operation(summary = "유저 삭제")
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<ApiResponseDto> deleteUser(@PathVariable Long userId) {
        ApiResponseDto apiResponseDto = adminService.deleteUser(userId);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    // 게시글 삭제
    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponseDto> deletePost(@PathVariable Long postId) {
        ApiResponseDto apiResponseDto = adminService.deletePost(postId);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    // 후기 글 삭제
    @Operation(summary = "리뷰 글 삭제")
    @DeleteMapping("/review-posts/{reviewPostId}")
    public ResponseEntity<ApiResponseDto> deleteReviewPost(@PathVariable Long reviewPostId) {
        ApiResponseDto apiResponseDto = adminService.deleteReviewPost(reviewPostId);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }


    /**
     * 백오피스에서 굳이 필요한 기능인지 몰라서 일단 보류하는 기능들
     */
    // 게시글 수정 -> 구현 x
    @Operation(summary = "게시글 수정")
    @PutMapping("/posts/{postId}")
    public ResponseEntity<ApiResponseDto> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto) {
        ApiResponseDto apiResponseDto = adminService.updatePost(postId, requestDto);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    // 게시글 수정 -> 구현 x
    @Operation(summary = "후기 게시글 수정")
    @PutMapping("/reviewPosts/{reviewPostId}")
    public ResponseEntity<ApiResponseDto> updateReviewPost(@PathVariable Long reviewPostId, @RequestBody ReviewPostRequestDto requestDto, @RequestParam(value = "images", required = false) List<MultipartFile> imageFiles) {
        ApiResponseDto apiResponseDto = adminService.updateReviewPost(reviewPostId, requestDto, imageFiles);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    //여행 일정 수정 -> 구현 x
    @Operation(summary = "여행 일정 수정")
    @PutMapping("/journeyList/{journeyListId}")
    public ResponseEntity<ApiResponseDto> updateJourneyList (@PathVariable Long journeyListId, @RequestBody JourneyListRequestDto requestDto) {
        ApiResponseDto apiResponseDto = adminService.updateJourneyList(journeyListId, requestDto);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    // 댓글 수정  -> 구현 x
    @Operation(summary = "댓글 수정")
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponseDto> updateComment(@PathVariable Long commentId, @RequestBody AdminCommentRequestDto requestDto) {
        ApiResponseDto apiResponseDto = adminService.updateComment(commentId, requestDto);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }


    // 댓글 관리는 상세 페이지로 이동해서 관리할 수 있도록 구현했기 때문에 필요 x
    // 여행 일정 삭제
    @Operation(summary = "여행 일정 삭제")
    @DeleteMapping("/journeyList/{journeyListId}")
    public ResponseEntity<ApiResponseDto> deleteJourneyList (@PathVariable Long journeyListId) {
        ApiResponseDto apiResponseDto = adminService.deleteJourneyList(journeyListId);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    // 댓글 삭제
    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponseDto> deleteComment(@PathVariable Long commentId) {
        ApiResponseDto apiResponseDto = adminService.deleteComment(commentId);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    // 대댓글 삭제
    @Operation(summary = "대댓글 삭제")
    @DeleteMapping("/replies/{replyId}")
    public ResponseEntity<ApiResponseDto> deleteReply(@PathVariable Long replyId) {
        ApiResponseDto apiResponseDto = adminService.deleteReply(replyId);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "대댓글 수정")
    @PutMapping("/replies/{replyId}")
    public ResponseEntity<ApiResponseDto> updateReply(@PathVariable Long replyId, @RequestBody ReplyRequestDto requestDto) {
        ApiResponseDto apiResponseDto = adminService.updateReply(replyId, requestDto);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }
}