package com.sparta.travelshooting.reply.dto;

import com.sparta.travelshooting.common.Timestamp;
import com.sparta.travelshooting.reply.entity.Reply;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReplyResponseDto extends Timestamp {
    private Long id;
    private String nickName;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ReplyResponseDto(Reply reply) {
        this.id = reply.getId();
        this.nickName = reply.getUser().getNickname();
        this.contents = reply.getContents();
        this.createdAt = reply.getCreatedAt();
        this.modifiedAt = reply.getModifiedAt();
    }
}
