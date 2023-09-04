package com.sparta.travelshooting.post.dto;

import com.sparta.travelshooting.comment.dto.CommentResponseDto;
import com.sparta.travelshooting.comment.entity.Comment;
import com.sparta.travelshooting.journeylist.dto.JourneyListResponseDto;
import com.sparta.travelshooting.post.entity.Post;
import com.sparta.travelshooting.user.entity.RegionEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class PostResponseDto {
    private Long id;
    private String nickName;
    private RegionEnum region;
    private String title;
    private String contents;
    private Integer likeCounts;
    private LocalDateTime createdAt;
    private List<CommentResponseDto> commentList;
    private List<JourneyListResponseDto> journeyList;


    public PostResponseDto (Post post) {
        this.id = post.getId();
        this.nickName = post.getUser().getNickname();
        this.region = post.getUser().getRegion();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.likeCounts = post.getLikeCounts();
        this.createdAt = post.getCreatedAt();
        this.commentList = post.getCommentList()
                .stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
        this.journeyList = post.getJourneyLists()
                .stream()
                .map(JourneyListResponseDto::new)
                .collect(Collectors.toList());
    }
}