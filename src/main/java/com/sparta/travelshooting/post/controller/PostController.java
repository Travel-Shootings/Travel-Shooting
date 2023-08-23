package com.sparta.travelshooting.post.controller;

import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.journeylist.dto.JourneyListRequestDto;
import com.sparta.travelshooting.journeylist.dto.JourneyListResponseDto;
import com.sparta.travelshooting.post.dto.*;
import com.sparta.travelshooting.post.service.PostService;
import com.sparta.travelshooting.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 글 생성
    @PostMapping()
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostResponseDto postResponseDto = postService.createPost(postRequestDto, userDetails.getUser());
        return new ResponseEntity<>(postResponseDto, HttpStatus.CREATED);
    }


    // 전체 게시글 조회
    @GetMapping()
    public ResponseEntity<List<PostResponseDto>> getPosts() {
        List<PostResponseDto> postResponseDto = postService.getPosts();
        return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
    }

    // 단건 게시글 조회
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long postId) {
        PostResponseDto postResponseDto = postService.getPost(postId);
        return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
    }

    // 게시글 수정
    @PutMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(@RequestBody PostRequestDto postRequestDto, @PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostResponseDto postResponseDto = postService.updatePost(postRequestDto, postId, userDetails.getUser());
        return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponseDto> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto apiResponseDto = postService.deletePost(postId, userDetails.getUser());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    //좋아요 기능
    @PostMapping("/{postId}/like")
    public ResponseEntity<ApiResponseDto> addLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto apiResponseDto = postService.addLike(postId, userDetails.getUser());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    //좋아요 취소 기능
    @DeleteMapping("/{postId}/like")
    public ResponseEntity<ApiResponseDto> deleteLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto apiResponseDto = postService.deleteLike(postId, userDetails.getUser());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }
}
