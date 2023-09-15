package com.sparta.travelshooting.comment.dto;

import com.sparta.travelshooting.comment.entity.Comment;
import com.sparta.travelshooting.reply.dto.ReplyResponseDto;
import com.sparta.travelshooting.reply.entity.Reply;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CommentResponseDto {
    private Long id;
    private String nickName;
    private String content;
    private List<ReplyResponseDto> replyList;


    public CommentResponseDto(Comment comment) {
        this.id = comment.getCommentId();
        this.nickName = comment.getUser().getNickname();
        this.content = comment.getContent();
        this.replyList = comment.getReplyList()
                .stream()
                .map(ReplyResponseDto::new)
                .collect(Collectors.toList());
    }
}

