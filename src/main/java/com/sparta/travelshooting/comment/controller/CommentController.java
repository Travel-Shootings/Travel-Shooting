package com.sparta.travelshooting.comment.controller;


import com.sparta.travelshooting.comment.dto.CommentRequestDto;
import com.sparta.travelshooting.comment.dto.CommentResponseDto;
import com.sparta.travelshooting.comment.service.CommentService;
import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/comments")
@Tag(name = "댓글 API")
public class CommentController {

    private final CommentService commentService;

    // 여행 계획 게시판 댓글 생성
    @Operation(summary = "여행 계획 게시판 댓글 생성")
    @PostMapping("/posts/{postId}")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto responseDto = commentService.createComment(postId, commentRequestDto, userDetails.getUser());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }


    // 여행 후기 게시판 댓글 생성
    @Operation(summary = "여행 후기 게시판 댓글 생성")
    @PostMapping("/reviewPost/{reviewPostId}")
    public ResponseEntity<CommentResponseDto> createCommentReview(@PathVariable Long reviewPostId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto responseDto = commentService.createCommentReview(reviewPostId, commentRequestDto, userDetails.getUser());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }


    // 댓글 수정
    @Operation(summary = "댓글 수정")
    @PutMapping("/{commentId}")
    public ResponseEntity<ApiResponseDto> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestdto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto responseDto = commentService.updateComment(commentId, commentRequestdto, userDetails.getUser());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    // 댓글 삭제
    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponseDto> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        ApiResponseDto responseDto = commentService.deleteComment(commentId, userDetails.getUser());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
