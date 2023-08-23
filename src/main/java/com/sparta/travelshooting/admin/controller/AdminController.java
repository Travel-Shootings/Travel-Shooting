package com.sparta.travelshooting.admin.controller;

import com.sparta.travelshooting.admin.dto.AdminProfileRequestDto;
import com.sparta.travelshooting.admin.service.AdminService;
import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.post.dto.PostRequestDto;
import com.sparta.travelshooting.post.dto.PostResponseDto;
import com.sparta.travelshooting.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

//    @Secured("Role_ADMIN")
//    @GetMapping("/summary")
//    public AdminSummaryResponseDto showSummary() {
//        return adminService.showSummary();
//    }

    // 유저 전체 정보 조회
    @Secured("Role_ADMIN")
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> showUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.showUsers());
    }

    // 글 전체 정보 조회
    @Secured("Role_ADMIN")
    @GetMapping("/posts")
    public List<PostResponseDto> showPosts() {
        return adminService.showPosts();
    }

    //유저 프로필 수정
    @Secured("Role_ADMIN")
    @PutMapping("/user/{userId}")
    public ResponseEntity<ApiResponseDto> updateUser(@PathVariable Long userId, @RequestBody AdminProfileRequestDto requestDto) {
        ApiResponseDto apiResponseDto = adminService.updateUser(userId, requestDto);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    // 게시글 수정
    @Secured("Role_ADMIN")
    @PutMapping("/posts/{postId}")
    public ResponseEntity<ApiResponseDto> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto) {
       ApiResponseDto apiResponseDto = adminService.updatePost(postId, requestDto);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    // 댓글 수정
}
