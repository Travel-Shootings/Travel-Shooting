package com.sparta.travelshooting.comment.controller;


import com.sparta.travelshooting.comment.dto.CommentRequestDto;
import com.sparta.travelshooting.comment.dto.CommentResponseDto;
import com.sparta.travelshooting.comment.service.CommentService;
import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;


    //댓글 생성
    @PostMapping("")
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        CommentResponseDto responseDto = commentService.createComment(commentRequestDto, userDetails.getUser());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }


    //댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<ApiResponseDto> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestdto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            commentService.updateComment(commentId, commentRequestdto, userDetails.getUser());
            return new ResponseEntity<>(new ApiResponseDto("댓글이 수정되었습니다", HttpStatus.OK.value()), HttpStatus.OK);
        } catch (RejectedExecutionException e) {
            return new ResponseEntity<>(new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.OK);
        }
    }


    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponseDto> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            commentService.deleteComment(commentId, userDetails.getUser());
            return new ResponseEntity<>(new ApiResponseDto("댓글이 삭제되었습니다", HttpStatus.OK.value()), HttpStatus.OK);
        } catch (RejectedExecutionException e) {
            return new ResponseEntity<>(new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
    }



}
