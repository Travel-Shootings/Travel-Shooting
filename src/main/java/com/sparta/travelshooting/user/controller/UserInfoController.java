package com.sparta.travelshooting.user.controller;

import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.security.UserDetailsImpl;
import com.sparta.travelshooting.user.dto.EditInfoRequestDto;
import com.sparta.travelshooting.user.dto.PasswordRequestDto;
import com.sparta.travelshooting.user.dto.UserResponseDto;
import com.sparta.travelshooting.user.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/my-page")
@RequiredArgsConstructor
@Tag(name = "사용자 마이페이지 API")
public class UserInfoController {

    private final UserInfoService userInfoService;

    // 마이페이지 : 자신의 정보 조회
    @Operation(summary = "자신의 정보 조회")
    @GetMapping
    public ResponseEntity<UserResponseDto> getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserResponseDto userResponseDto = userInfoService.getUserInfo(userDetails.getUser());
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    // 마이페이지 : 자신의 정보 수정 (nickname, region)
    @Operation(summary = "자신의 정보 수정")
    @PutMapping("/edit")
    public ResponseEntity<ApiResponseDto> editUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody EditInfoRequestDto requestDto) {
        ApiResponseDto userResponseDto = userInfoService.editUserInfo(userDetails.getUser(), requestDto);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    // 마이페이지 : 비밀번호 수정
    @Operation(summary = "비밀번호 수정")
    @PutMapping("/edit/password")
    public ResponseEntity<ApiResponseDto> editUserPassword(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PasswordRequestDto requestDto) {
        ApiResponseDto apiResponseDto = userInfoService.editUserPassword(userDetails.getUser(), requestDto);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

}
