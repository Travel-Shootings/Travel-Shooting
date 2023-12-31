package com.sparta.travelshooting.reviewPost.dto;


import com.sparta.travelshooting.S3Image.entity.Image;
import com.sparta.travelshooting.comment.dto.CommentResponseDto;
import com.sparta.travelshooting.reviewPost.entity.ReviewPost;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ReviewPostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String nickName;
    private List<String> imageUrls;
    private Integer likeCounts;
    private LocalDateTime createdAt;
    private List<CommentResponseDto> commentList;


    public ReviewPostResponseDto(ReviewPost reviewPost) {
        this.id = reviewPost.getId();
        this.title = reviewPost.getTitle();
        this.content = reviewPost.getContent();
        this.nickName = reviewPost.getUser().getNickname();
        this.likeCounts = reviewPost.getLikeCounts();
        this.imageUrls = reviewPost.getImages().stream()
                .map(Image::getAccessUrl)
                .collect(Collectors.toList());
        this.createdAt = reviewPost.getCreatedAt();
        this.commentList = reviewPost.getCommentList()
                .stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }
}


