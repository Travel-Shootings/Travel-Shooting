package com.sparta.travelshooting.reviewPost.dto;

import com.sparta.travelshooting.reviewPost.entity.ReviewPost;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewPostListResponseDto {


    private Long id;
    private String title;
    private String content;
    private String nickName;
    private Integer likeCounts;
    private LocalDateTime createdAt;




    public ReviewPostListResponseDto(ReviewPost reviewPost){
        this.id = reviewPost.getId();
        this.title = reviewPost.getTitle();
        this.content = reviewPost.getContent();
        this.nickName= reviewPost.getUser().getNickname();
        this.likeCounts = reviewPost.getLikeCounts();
        this.createdAt = reviewPost.getCreatedAt();
    }
}



