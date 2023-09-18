package com.sparta.travelshooting.reply.service;

import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.reply.dto.ReplyRequestDto;
import com.sparta.travelshooting.reply.dto.ReplyResponseDto;
import com.sparta.travelshooting.user.entity.User;

public interface ReplyService {

    //대댓글 생성
    ReplyResponseDto createReply(Long commentId, ReplyRequestDto requestDto, User user);

    //대댓글 수정
    ApiResponseDto updateReply(Long replyId, ReplyRequestDto replyRequestDto, User user);

    //대댓글 삭제
    ApiResponseDto deleteReply(Long replyId, User user);


}
