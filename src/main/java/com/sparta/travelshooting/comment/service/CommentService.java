package com.sparta.travelshooting.comment.service;


import com.sparta.travelshooting.comment.dto.CommentRequestDto;
import com.sparta.travelshooting.comment.dto.CommentResponseDto;
import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.user.entity.User;

import java.util.List;

public interface CommentService {

    //여행 계획 게시판 댓글 생성
    CommentResponseDto createComment(Long postId, CommentRequestDto requestDto, User user);

    //여행 후기 게시판 댓글 생성
    CommentResponseDto createCommentReview(Long reviewPostId, CommentRequestDto requestDto, User user);

    //댓글 수정
    ApiResponseDto updateComment(Long commentId, CommentRequestDto requestDto, User user);

    //댓글 삭제
    ApiResponseDto deleteComment(Long id, User user);

}
