package com.sparta.travelshooting.post.service;

import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.post.dto.PostRequestDto;
import com.sparta.travelshooting.post.dto.PostResponseDto;
import com.sparta.travelshooting.user.entity.User;

import java.util.List;

public interface PostService {


     // 게시글 작성
     PostResponseDto createPost(PostRequestDto postRequestDto, User user);

     //게시글 전체 조회
     List<PostResponseDto> getPosts();

     //게시글 단건 조회
     PostResponseDto getPost(Long postId);

     //게시글 수정
     PostResponseDto updatePost(PostRequestDto postRequestDto, Long postId, User user);

     //게시글 삭제
     ApiResponseDto deletePost(Long postId, User user);

     //좋아요 추가
     ApiResponseDto addLike(Long postId, User user);

     //좋아요 삭제
     ApiResponseDto deleteLike(Long postId, User user);
}


