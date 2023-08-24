package com.sparta.travelshooting.journeylist.controller;

import com.sparta.travelshooting.journeylist.dto.JourneyListRequestDto;
import com.sparta.travelshooting.journeylist.dto.JourneyListResponseDto;
import com.sparta.travelshooting.journeylist.service.JourneyListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api")
@Tag(name = "여행 일정 API")
public class JourneyListController {

    private final JourneyListService journeyListService;

    // 여행 일정 생성
    @Operation(summary = "여행 일정 생성")
    @PostMapping("/posts/{postId}/journeyList")
    public ResponseEntity<JourneyListResponseDto> createJourney(@PathVariable Long postId, @RequestBody JourneyListRequestDto journeyListRequestDto) {
        JourneyListResponseDto journeyListResponseDto = journeyListService.createJourney(postId, journeyListRequestDto);
        return ResponseEntity.ok().body(journeyListResponseDto);
    }
}
