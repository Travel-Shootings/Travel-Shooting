package com.sparta.travelshooting.post.dto;

import com.sparta.travelshooting.journeylist.dto.JourneyListRequestDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class PostAndJourneyListDto {
    private PostRequestDto postRequestDto;
    private List<JourneyListRequestDto> journeyListRequestDtos;
}


