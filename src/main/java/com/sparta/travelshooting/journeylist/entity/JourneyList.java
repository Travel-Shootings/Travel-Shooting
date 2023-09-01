package com.sparta.travelshooting.journeylist.entity;

import com.sparta.travelshooting.journeylist.dto.JourneyListRequestDto;
import com.sparta.travelshooting.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@Entity
@Table(name = "post_journeyList")
public class JourneyList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String locations;

    @Column(nullable = false)
    private Long budget;

    @Column
    private LocalDateTime startJourney;

    @Column
    private LocalDateTime endJourney;

    @Column(nullable = false)
    private Integer members;

    @Column(nullable = false)
    private String placeAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public JourneyList(JourneyListRequestDto requestDto, Post post) {
        this.locations = requestDto.getLocations();
        this.budget = requestDto.getBudget();
        this.startJourney = requestDto.getStartJourney();
        this.endJourney = requestDto.getEndJourney();
        this.members = requestDto.getMembers();
        this.placeAddress = requestDto.getPlaceAddress();
        this.post = post;
    }

    public void update(JourneyListRequestDto requestDto) {
        this.locations = requestDto.getLocations();
        this.budget = requestDto.getBudget();
        this.startJourney = requestDto.getStartJourney();
        this.endJourney = requestDto.getEndJourney();
        this.members = requestDto.getMembers();
        this.placeAddress = requestDto.getPlaceAddress();
    }

    public void updateByAdmin(JourneyListRequestDto requestDto) {
        this.locations = requestDto.getLocations();
        this.budget = requestDto.getBudget();
        this.startJourney = requestDto.getStartJourney();
        this.endJourney = requestDto.getEndJourney();
        this.members = requestDto.getMembers();
        this.placeAddress = requestDto.getPlaceAddress();
    }
}
