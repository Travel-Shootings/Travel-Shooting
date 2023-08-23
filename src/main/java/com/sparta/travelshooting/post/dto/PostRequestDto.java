package com.sparta.travelshooting.post.dto;

import com.sparta.travelshooting.journeylist.entity.JourneyList;
import lombok.Getter;

import java.util.List;

@Getter
public class PostRequestDto {
    private String title;
    private String contents;
}