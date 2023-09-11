package com.sparta.travelshooting.reviewPost.dto;

import com.sparta.travelshooting.S3Image.entity.Image;
import com.sparta.travelshooting.reviewPost.entity.ReviewPost;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class HomeReviewResponseDto {
    private Long id;
    private List<String> imageUrls;

    public HomeReviewResponseDto(ReviewPost reviewPost) {
        this.id = reviewPost.getId();
        this.imageUrls = reviewPost.getImages().stream()
                .map(Image::getAccessUrl)
                .collect(Collectors.toList());
    }
}
