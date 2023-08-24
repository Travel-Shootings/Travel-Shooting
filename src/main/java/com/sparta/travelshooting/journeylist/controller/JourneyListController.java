package com.sparta.travelshooting.journeylist.controller;

import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.journeylist.dto.JourneyListRequestDto;
import com.sparta.travelshooting.journeylist.dto.JourneyListResponseDto;
import com.sparta.travelshooting.journeylist.service.JourneyListServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class JourneyListController {

    private final JourneyListServiceImpl journeyListServiceImpl;

    // 여행 일정 생성
    @PostMapping("/{postId}/journeyList")
    public ResponseEntity<JourneyListResponseDto> createJourney(@PathVariable Long postId, @RequestBody JourneyListRequestDto journeyListRequestDto) {
        JourneyListResponseDto journeyListResponseDto = journeyListServiceImpl.createJourney(postId, journeyListRequestDto);
        return ResponseEntity.ok().body(journeyListResponseDto);
    }

    // 여행 일정 수정
    @PutMapping("/{postId}/journeyList/{journeyListId}")
    public ResponseEntity<ApiResponseDto> updateJourney (@PathVariable Long postId, @PathVariable Long journeyListId, @RequestBody JourneyListRequestDto journeyListRequestDto) {
        ApiResponseDto apiResponseDto = journeyListServiceImpl.updateJourney(postId,journeyListId, journeyListRequestDto);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    // 여행 일정 삭제
    @DeleteMapping("/{postId}/journeyList/{journeyListId}")
    public ResponseEntity<ApiResponseDto> deleteJourney (@PathVariable Long postId, @PathVariable Long journeyListId) {
        ApiResponseDto apiResponseDto = journeyListServiceImpl.deleteJourney(postId, journeyListId);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }
}
