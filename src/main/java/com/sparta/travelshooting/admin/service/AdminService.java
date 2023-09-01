package com.sparta.travelshooting.admin.service;

import com.sparta.travelshooting.admin.dto.AdminCommentRequestDto;
import com.sparta.travelshooting.admin.dto.AdminProfileRequestDto;
import com.sparta.travelshooting.admin.dto.AdminSummaryResponseDto;
import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.journeylist.dto.JourneyListRequestDto;
import com.sparta.travelshooting.post.dto.PostRequestDto;
import com.sparta.travelshooting.post.dto.PostResponseDto;
import com.sparta.travelshooting.user.dto.UserResponseDto;

import java.util.List;

public interface AdminService {

    //총 유저 수, 총 게시글 수 , 총 댓글 수 조회
    AdminSummaryResponseDto showSummary();

    // 전체 유저 정보 조회 -> 추후 QueryDsl로 수정 예정
    List<UserResponseDto> showUsers();

    // 글 전체 조회
    List<PostResponseDto> showPosts();

    // 유저 정보 수정
    ApiResponseDto updateUser(Long userId, AdminProfileRequestDto requestDto);

    // 게시글 수정
    ApiResponseDto updatePost(Long postId, PostRequestDto requestDto);

    // 여행 일정 수정
    ApiResponseDto updateJourneyList(Long journeyListId, JourneyListRequestDto requestDto);

    // 댓글 수정
    ApiResponseDto updateComment(Long commentId, AdminCommentRequestDto requestDto);

    // 유저 삭제
    ApiResponseDto deleteUser(Long userId);

    //게시글 삭제
    ApiResponseDto deletePost(Long postId);

    // 여행 일정 삭제
    ApiResponseDto deleteJourneyList(Long journeyListId);

    //댓글 삭제
    ApiResponseDto deleteComment(Long commentId);

    //유저 정보 조회
    UserResponseDto showUser(Long userId);
}
