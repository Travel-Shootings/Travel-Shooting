package com.sparta.travelshooting.post.dto;

import com.sparta.travelshooting.journeylist.dto.JourneyListResponseDto;
import com.sparta.travelshooting.post.entity.Post;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class PostResponseDto {
    private String title;
    private String contents;
    private Integer likeCounts;
    private List<JourneyListResponseDto> journeyList;


    public PostResponseDto (Post post) {
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.likeCounts = post.getLikeCounts();
        this.journeyList = post.getJourneyLists()
                .stream()
                .map(JourneyListResponseDto::new)
                .collect(Collectors.toList());
    }
}