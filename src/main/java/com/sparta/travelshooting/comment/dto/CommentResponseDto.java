package com.sparta.travelshooting.comment.dto;

import com.sparta.travelshooting.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private String nickName;
    private String content;


    public CommentResponseDto(Comment comment) {
        this.id = comment.getCommentId();
        this.nickName = comment.getUser().getNickname();
        this.content = comment.getContent();
    }
}
