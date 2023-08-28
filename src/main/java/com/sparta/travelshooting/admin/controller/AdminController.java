package com.sparta.travelshooting.admin.controller;

import com.sparta.travelshooting.admin.dto.AdminCommentRequestDto;
import com.sparta.travelshooting.admin.dto.AdminProfileRequestDto;
import com.sparta.travelshooting.admin.dto.AdminSummaryResponseDto;
import com.sparta.travelshooting.admin.service.AdminServiceImpl;
import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.journeylist.dto.JourneyListRequestDto;
import com.sparta.travelshooting.post.dto.PostRequestDto;
import com.sparta.travelshooting.post.dto.PostResponseDto;
import com.sparta.travelshooting.user.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//@Secured("ADMIN")
@RequiredArgsConstructor
@Controller
@RequestMapping("/api/admin")
@Tag(name = "관리자용 API")
public class AdminController {

    private final AdminServiceImpl adminServiceImpl;

    @GetMapping("/summary")
    public ResponseEntity<AdminSummaryResponseDto> showSummary() {
        AdminSummaryResponseDto adminSummaryResponseDto = adminServiceImpl.showSummary();
        return new ResponseEntity<>(adminSummaryResponseDto, HttpStatus.OK);
    }

    // 유저 전체 정보 조회
    @Operation(summary = "유저 전체 정보 조회")
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> showUsers() {
        List<UserResponseDto> userResponseDto = adminServiceImpl.showUsers();
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    // 글 전체 정보 조회
    @Operation(summary = "글 전체 정보 조회")
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>> showPosts() {
        List<PostResponseDto> postResponseDto = adminServiceImpl.showPosts();
        return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
    }

    //유저 프로필 수정
    @Operation(summary = "유저 프로필 수정")
    @PutMapping("/user/{userId}")
    public ResponseEntity<ApiResponseDto> updateUser(@PathVariable Long userId, @RequestBody AdminProfileRequestDto requestDto) {
        ApiResponseDto apiResponseDto = adminServiceImpl.updateUser(userId, requestDto);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    // 게시글 수정
    @Operation(summary = "게시글 수정")
    @PutMapping("/posts/{postId}")
    public ResponseEntity<ApiResponseDto> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto) {
        ApiResponseDto apiResponseDto = adminServiceImpl.updatePost(postId, requestDto);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    //여행 일정 수정
    @Operation(summary = "여행 일정 수정")
    @PutMapping("/journeyList/{journeyListId}")
    public ResponseEntity<ApiResponseDto> updateJourneyList (@PathVariable Long journeyListId, @RequestBody JourneyListRequestDto requestDto) {
        ApiResponseDto apiResponseDto = adminServiceImpl.updateJourneyList(journeyListId, requestDto);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    // 댓글 수정
    @Operation(summary = "댓글 수정")
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponseDto> updateComment(@PathVariable Long commentId, @RequestBody AdminCommentRequestDto requestDto) {
        ApiResponseDto apiResponseDto = adminServiceImpl.updateComment(commentId, requestDto);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    // 유저 삭제
    @Operation(summary = "유저 삭제")
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<ApiResponseDto> deleteUser(@PathVariable Long userId) {
        ApiResponseDto apiResponseDto = adminServiceImpl.deleteUser(userId);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    // 게시글 삭제
    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponseDto> deletePost(@PathVariable Long postId) {
        ApiResponseDto apiResponseDto = adminServiceImpl.deletePost(postId);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    // 여행 일정 삭제
    @Operation(summary = "여행 일정 삭제")
    @DeleteMapping("/journeyList/{journeyListId}")
    public ResponseEntity<ApiResponseDto> deleteJourneyList (@PathVariable Long journeyListId) {
        ApiResponseDto apiResponseDto = adminServiceImpl.deleteJourneyList(journeyListId);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    // 댓글 삭제
    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponseDto> updateComment(@PathVariable Long commentId) {
        ApiResponseDto apiResponseDto = adminServiceImpl.deleteComment(commentId);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }
}

