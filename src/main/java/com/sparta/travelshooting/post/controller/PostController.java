package com.sparta.travelshooting.post.controller;

import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.post.dto.PostAndJourneyListDto;
import com.sparta.travelshooting.post.dto.PostListResponseDto;
import com.sparta.travelshooting.post.dto.PostResponseDto;
import com.sparta.travelshooting.post.service.PostService;
import com.sparta.travelshooting.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Tag(name = "게시글 API")
public class PostController {

    private final PostService postService;

    // 글 생성
    @Operation(summary = "게시글과 여행일정 생성")
    @PostMapping()
    public ResponseEntity<PostResponseDto> createPostAndJourneyList(@RequestBody PostAndJourneyListDto postAndJourneyListDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostResponseDto postResponseDto = postService.createPostAndJourneyList(postAndJourneyListDto, userDetails.getUser());
        return new ResponseEntity<>(postResponseDto, HttpStatus.CREATED);
    }

    // 3개 게시글 조회(Home 화면)
    @Operation(summary = "메인 페이지 게시글 3개 출력")
    @GetMapping("/three")
    public ResponseEntity<List<PostListResponseDto>> getThreePosts() {
        List<PostListResponseDto> postResponseDto = postService.getThreePosts();
        return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
    }

    // 전체 게시글 조회
    @Operation(summary = "전체 게시글과 여행일정 조회")
    @GetMapping()
    public ResponseEntity<List<PostListResponseDto>> getPosts() {
        List<PostListResponseDto> postResponseDto = postService.getPosts();
        return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
    }

    // 단건 게시글 조회
    @Operation(summary = "단건 게시글과 여행일정 조회")
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long postId) {
        PostResponseDto postResponseDto = postService.getPost(postId);
        return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
    }

    // 단건 게시글 수정 페이지 조회 (게시글 수정 페이지 접속)
    @Operation(summary = "단건 게시글과 여행일정 조회")
    @GetMapping("/update/{postId}")
    public ResponseEntity<PostResponseDto> updatePost (@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostResponseDto postResponseDto = postService.updatePost(postId, userDetails.getUser());
        return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
    }


    // 게시글 수정
    @Operation(summary = "게시글 수정")
    @PutMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updatePostAndJourneyList(@RequestBody PostAndJourneyListDto postAndJourneyListDto, @PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostResponseDto postResponseDto = postService.updatePostAndJourneyList(postAndJourneyListDto, postId, userDetails.getUser());
        return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
    }

    // 게시글 삭제
    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponseDto> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto apiResponseDto = postService.deletePost(postId, userDetails.getUser());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    //좋아요 기능
    @Operation(summary = "게시글 좋아요")
    @PostMapping("/{postId}/like")
    public ResponseEntity<ApiResponseDto> addLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto apiResponseDto = postService.addLike(postId, userDetails.getUser());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    //좋아요 취소 기능
    @Operation(summary = "게시글 좋아요 취소")
    @DeleteMapping("/{postId}/like")
    public ResponseEntity<ApiResponseDto> deleteLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto apiResponseDto = postService.deleteLike(postId, userDetails.getUser());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    // 6개 게시글 조회(게시글 목록 화면)
    @Operation(summary = "post 6개 조회")
    @GetMapping("/page")
    public ResponseEntity<Page<PostListResponseDto>> findPosts(@PageableDefault(size = 6) Pageable pageable) {
        Page<PostListResponseDto> postResponseDto = postService.findPosts(pageable);
        return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
    }
}
