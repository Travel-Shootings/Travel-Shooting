package com.sparta.travelshooting.reviewPost.dto;


import com.sparta.travelshooting.reviewPost.entity.ReviewPost;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewPostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String imageUrl;



    public ReviewPostResponseDto(ReviewPost reviewPost){
        this.id = reviewPost.getId();
        this.title = reviewPost.getTitle();
        this.content = reviewPost.getContent();
        this.imageUrl = reviewPost.getImageUrl();
    }
}


