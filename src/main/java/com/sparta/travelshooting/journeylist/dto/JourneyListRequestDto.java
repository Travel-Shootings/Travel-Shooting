package com.sparta.travelshooting.journeylist.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class JourneyListRequestDto {
    private String locations;
    private Long budget;
    private LocalDateTime period;
    private Integer members;
}
