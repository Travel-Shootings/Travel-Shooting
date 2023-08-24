package com.sparta.travelshooting.journeylist.service;

import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.journeylist.dto.JourneyListRequestDto;
import com.sparta.travelshooting.journeylist.dto.JourneyListResponseDto;

public interface JourneyListService {

    // 여행 일정(팝업창) 생성
    JourneyListResponseDto createJourney(Long postId, JourneyListRequestDto journeyListRequestDto);

    // 여행 일정 수정
    ApiResponseDto updateJourney(Long postId, Long journeyListId, JourneyListRequestDto journeyListRequestDto);

    // 여행 일정 삭제
    ApiResponseDto deleteJourney(Long postId, Long journeyListId);
}
