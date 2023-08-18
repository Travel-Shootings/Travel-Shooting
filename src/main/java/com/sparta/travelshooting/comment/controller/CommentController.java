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
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;


    //댓글 생성
    @PostMapping("")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.createComment(commentRequestDto, userDetails.getUser());
    }


    //댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<ApiResponseDto> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestdto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            commentService.updateComment(commentId, commentRequestdto, userDetails.getUser());
        } catch (RejectedExecutionException e) {
            ApiResponseDto apiResponseDto = new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);}

        ApiResponseDto apiResponseDto = new ApiResponseDto("댓글이 수정되었습니다",HttpStatus.OK.value());
        return  new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }


    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponseDto> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        try{
            commentService.deleteComment(commentId, userDetails.getUser());
        }
        catch (RejectedExecutionException e){
            ApiResponseDto apiResponseDto = new ApiResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(apiResponseDto,HttpStatus.BAD_REQUEST);
        }
        ApiResponseDto apiResponseDto = new ApiResponseDto("댓글이 삭제되었습니다",HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }



}
