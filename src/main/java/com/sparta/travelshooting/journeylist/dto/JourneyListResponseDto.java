package com.sparta.travelshooting.journeylist.dto;

import com.sparta.travelshooting.journeylist.entity.JourneyList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
public class JourneyListResponseDto {
    private String locations;
    private Long budget;
    private LocalDateTime startJourney;
    private LocalDateTime endJourney;
    private Integer members;
    private String placeAddress;

    public JourneyListResponseDto (JourneyList journeyList) {
        this.locations = journeyList.getLocations();
        this.budget = journeyList.getBudget();
        this.startJourney = journeyList.getStartJourney();
        this.endJourney = journeyList.getEndJourney();
        this.members = journeyList.getMembers();
        this.placeAddress = journeyList.getPlaceAddress();
    }
}
