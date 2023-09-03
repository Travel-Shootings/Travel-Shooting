package com.sparta.travelshooting.post.dto;

import com.sparta.travelshooting.post.entity.Post;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class PostListResponseDto {
    private Long id;
    private String nickName;
    private String title;
    private String contents;
    private Integer likeCounts;
    private LocalDateTime createdAt;

    public PostListResponseDto(Post post) {
        this.id = post.getId();
        this.nickName = post.getUser().getNickname();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.likeCounts = post.getLikeCounts();
        this.createdAt = post.getCreatedAt();
    }
}
