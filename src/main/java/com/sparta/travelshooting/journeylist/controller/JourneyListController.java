package com.sparta.travelshooting.journeylist.controller;

import com.sparta.travelshooting.journeylist.dto.JourneyListRequestDto;
import com.sparta.travelshooting.journeylist.dto.JourneyListResponseDto;
import com.sparta.travelshooting.journeylist.service.JourneyListService;
import com.sparta.travelshooting.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class JourneyListController {

    private final JourneyListService journeyListService;

//     여행 일정 생성
    @PostMapping("/posts/{postId}/journeyList")
    public ResponseEntity<JourneyListResponseDto> createJourney(@PathVariable Long postId, @RequestBody JourneyListRequestDto journeyListRequestDto) {
        JourneyListResponseDto journeyListResponseDto = journeyListService.createJourney(postId, journeyListRequestDto);
        return ResponseEntity.ok().body(journeyListResponseDto);
    }
}
