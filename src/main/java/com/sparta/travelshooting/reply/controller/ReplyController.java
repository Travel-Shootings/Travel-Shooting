package com.sparta.travelshooting.reply.controller;


import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.reply.dto.ReplyRequestDto;
import com.sparta.travelshooting.reply.dto.ReplyResponseDto;
import com.sparta.travelshooting.reply.service.ReplyService;
import com.sparta.travelshooting.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/comments/{commentId}/replies")
public class ReplyController {

    private final ReplyService replyService;

    //대댓글생성
    @PostMapping("")
    public ResponseEntity<ReplyResponseDto> createReply(@PathVariable Long commentId, @RequestBody ReplyRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        ReplyResponseDto replyResponseDto = replyService.createReply(requestDto, userDetails.getUser());
        return new ResponseEntity<>(replyResponseDto, HttpStatus.CREATED);
    }


    // 대댓글 수정
    @PutMapping("/{replyId}")
    public ResponseEntity<ApiResponseDto> updateReply(@PathVariable Long replyId, @RequestBody ReplyRequestDto replyRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            replyService.updateReply(replyId, replyRequestDto, userDetails.getUser());
        } catch (RejectedExecutionException e) {
            ApiResponseDto apiResponseDto = new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
        }

        ApiResponseDto apiResponseDto = new ApiResponseDto("대댓글이 수정되었습니다", HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{replyId}")
    public ResponseEntity<ApiResponseDto> deleteReply(@PathVariable Long replyId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        try{
            replyService.deleteReply(replyId, userDetails.getUser());
        }catch (RejectedExecutionException e){
            ApiResponseDto apiResponseDto = new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
        }
        ApiResponseDto apiResponseDto = new ApiResponseDto("대댓글이 삭제되었습니다", HttpStatus.OK.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }





}
