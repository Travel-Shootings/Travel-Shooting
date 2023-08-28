package com.sparta.travelshooting.journeylist.controller;

import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.journeylist.dto.JourneyListRequestDto;
import com.sparta.travelshooting.journeylist.dto.JourneyListResponseDto;
import com.sparta.travelshooting.journeylist.service.JourneyListServiceImpl;
import com.sparta.travelshooting.journeylist.service.JourneyListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/posts")
@Tag(name = "여행 일정 API")
public class JourneyListController {

    private final JourneyListServiceImpl journeyListServiceImpl;

    // 여행 일정 생성
    @Operation(summary = "여행 일정 생성")
    @PostMapping("/{postId}/journeyList")
    public ResponseEntity<JourneyListResponseDto> createJourney(@PathVariable Long postId, @RequestBody JourneyListRequestDto journeyListRequestDto) {
        JourneyListResponseDto journeyListResponseDto = journeyListServiceImpl.createJourney(postId, journeyListRequestDto);
        return ResponseEntity.ok().body(journeyListResponseDto);
    }

    // 여행 일정 수정
    @Operation(summary = "여행 일정 수정")
    @PutMapping("/{postId}/journeyList/{journeyListId}")
    public ResponseEntity<ApiResponseDto> updateJourney (@PathVariable Long postId, @PathVariable Long journeyListId, @RequestBody JourneyListRequestDto journeyListRequestDto) {
        ApiResponseDto apiResponseDto = journeyListServiceImpl.updateJourney(postId,journeyListId, journeyListRequestDto);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    // 여행 일정 삭제
    @Operation(summary = "여행 일정 삭제")
    @DeleteMapping("/{postId}/journeyList/{journeyListId}")
    public ResponseEntity<ApiResponseDto> deleteJourney (@PathVariable Long postId, @PathVariable Long journeyListId) {
        ApiResponseDto apiResponseDto = journeyListServiceImpl.deleteJourney(postId, journeyListId);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }
}
