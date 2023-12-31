package com.sparta.travelshooting.journeylist.service;

import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.journeylist.dto.JourneyListRequestDto;

public interface JourneyListService {

    // 여행 일정 수정
    ApiResponseDto updateJourney(Long postId, Long journeyListId, JourneyListRequestDto journeyListRequestDto);

    // 여행 일정 삭제
    ApiResponseDto deleteJourney(Long postId, Long journeyListId);
}
