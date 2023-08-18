package com.sparta.travelshooting.comment.service;


import com.sparta.travelshooting.comment.dto.CommentRequestDto;
import com.sparta.travelshooting.comment.dto.CommentResponseDto;
import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.user.entity.User;

public interface CommentService {

    CommentResponseDto createComment(CommentRequestDto requestDto, User user);

    ApiResponseDto updateComment(Long commentId, CommentRequestDto requestDto, User user);

    ApiResponseDto deleteComment(Long id, User user);


}
