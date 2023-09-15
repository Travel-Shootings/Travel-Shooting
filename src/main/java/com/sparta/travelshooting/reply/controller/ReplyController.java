package com.sparta.travelshooting.reply.controller;


import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.reply.dto.ReplyRequestDto;
import com.sparta.travelshooting.reply.dto.ReplyResponseDto;
import com.sparta.travelshooting.reply.service.ReplyService;
import com.sparta.travelshooting.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/replies")
@Tag(name = "대댓글 API")
public class ReplyController {

    private final ReplyService replyService;

    //대댓글 생성
    @Operation(summary = "대댓글 생성")
    @PostMapping("/{commentId}")
    public ResponseEntity<ReplyResponseDto> createReply(@PathVariable Long commentId, @RequestBody ReplyRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ReplyResponseDto replyResponseDto = replyService.createReply(commentId, requestDto, userDetails.getUser());
        return new ResponseEntity<>(replyResponseDto, HttpStatus.CREATED);
    }


    // 대댓글 수정
    @Operation(summary = "대댓글 수정")
    @PutMapping("/{replyId}")
    public ResponseEntity<ApiResponseDto> updateReply(@PathVariable Long replyId, @RequestBody ReplyRequestDto replyRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto apiResponseDto = replyService.updateReply(replyId, replyRequestDto, userDetails.getUser());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    //대댓글 삭제
    @Operation(summary = "대댓글 삭제")
    @DeleteMapping("/{replyId}")
    public ResponseEntity<ApiResponseDto> deleteReply(@PathVariable Long replyId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto apiResponseDto = replyService.deleteReply(replyId, userDetails.getUser());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }
}
