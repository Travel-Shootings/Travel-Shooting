package com.sparta.travelshooting.comment.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {

    Long postId;
    String contents;
}
