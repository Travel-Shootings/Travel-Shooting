package com.sparta.travelshooting.journeylist.dto;

import com.sparta.travelshooting.journeylist.entity.JourneyList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class JourneyListResponseDto {
    private String locations;
    private Long budget;
    private LocalDateTime period;
    private Integer members;

    public JourneyListResponseDto (JourneyList journeyList) {
        this.locations = journeyList.getLocations();
        this.budget = journeyList.getBudget();
        this.period = journeyList.getPeriod();
        this.members = journeyList.getMembers();
    }
}
