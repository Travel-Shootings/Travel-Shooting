package com.sparta.travelshooting.comment.dto;

import com.sparta.travelshooting.comment.entity.Comment;
import com.sparta.travelshooting.common.Timestamp;

import java.time.LocalDateTime;

public class CommentResponseDto extends Timestamp {
    private Long id;
    private String nickName;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


    public CommentResponseDto(Comment comment) {
        this.id = comment.getCommentId();
        this.nickName = comment.getUser().getNickname();
        this.contents = comment.getContents();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();

    }
}
