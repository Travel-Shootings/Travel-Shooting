package com.sparta.travelshooting.chat.dto;

import com.sparta.travelshooting.chat.entity.ChatMessage;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatMessageResponseDto {
    private final String senderName;
    private final String content;
    private final LocalDateTime time;

    @Builder
    public ChatMessageResponseDto(ChatMessage chatMessage) {
        this.senderName = chatMessage.getSenderName();
        this.content = chatMessage.getContent();
        this.time = chatMessage.getTime();
    }
}
