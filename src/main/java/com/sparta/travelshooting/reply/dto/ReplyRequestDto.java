package com.sparta.travelshooting.reply.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReplyRequestDto {
    private Long commentId;
    private String content;
}
