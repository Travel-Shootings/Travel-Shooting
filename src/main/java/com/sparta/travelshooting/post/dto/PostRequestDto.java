package com.sparta.travelshooting.post.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PostRequestDto {
    @NotNull
    private String title;
    @NotNull
    private String contents;
}