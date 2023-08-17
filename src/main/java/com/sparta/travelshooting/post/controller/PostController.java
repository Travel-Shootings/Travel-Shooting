package com.sparta.travelshooting.post.controller;

import com.sparta.travelshooting.post.dto.ApiResponseDto;
import com.sparta.travelshooting.post.dto.PostRequestDto;
import com.sparta.travelshooting.post.dto.PostResponseDto;
import com.sparta.travelshooting.post.service.PostService;
import com.sparta.travelshooting.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    // 글 생성
    @PostMapping("/posts")
    public ResponseEntity<PostResponseDto> createPost (@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostResponseDto postResponseDto = postService.createPost(postRequestDto, userDetails.getUser());
        return ResponseEntity.status(201).body(postResponseDto);
    }

    // 전체 게시글 조회
    @GetMapping("/posts")
    public List<PostResponseDto> getPosts () {
        return postService.getPosts();
    }

    // 단건 게시글 조회
    @GetMapping("/posts/{postId}")
    public PostResponseDto getPost (@PathVariable Long postId) {
        return postService.getPost(postId);
    }

    // 게시글 수정
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDto> updatePost (@RequestBody PostRequestDto postRequestDto, @PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostResponseDto postResponseDto = postService.updatePost(postRequestDto, postId, userDetails.getUser());
        return ResponseEntity.status(201).body(postResponseDto);
    }

    // 게시글 삭제
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponseDto> deletePost (@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(postId, userDetails.getUser());
        return ResponseEntity.status(201).body(new ApiResponseDto("게시글 삭제 성공", 201));
    }
}
