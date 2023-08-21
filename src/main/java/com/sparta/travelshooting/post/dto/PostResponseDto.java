package com.sparta.travelshooting.post.dto;

import com.sparta.travelshooting.post.entity.Post;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class PostResponseDto {
    private String title;
    private String contents;
    private LocalDateTime createdAt;

    public PostResponseDto (Post post) {
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
    }
}