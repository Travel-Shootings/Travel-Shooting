package com.sparta.travelshooting.comment.service;


import com.sparta.travelshooting.comment.dto.CommentRequestDto;
import com.sparta.travelshooting.comment.dto.CommentResponseDto;
import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.user.entity.User;

public interface CommentService {

    //댓글 생성
    CommentResponseDto createComment(CommentRequestDto requestDto, User user);

    //댓글 수정
    ApiResponseDto updateComment(Long commentId, CommentRequestDto requestDto, User user);


    //댓글 삭제
    ApiResponseDto deleteComment(Long id, User user);


}
