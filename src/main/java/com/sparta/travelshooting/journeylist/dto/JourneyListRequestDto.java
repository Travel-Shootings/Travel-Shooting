package com.sparta.travelshooting.journeylist.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class JourneyListRequestDto {
    private String locations;
    private Long budget;
    private LocalDateTime startJourney;
    private LocalDateTime endJourney;
    private Integer members;
    private String placeAddress;
}
