package com.sparta.travelshooting.chat.dto;

import com.sparta.travelshooting.chat.entity.ChatMessage;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class ChatMessageResponseDto {
    private final String senderName;
    private final String content;
    private final String simpleTime;

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Builder
    public ChatMessageResponseDto(ChatMessage chatMessage) {
        this.senderName = chatMessage.getSenderName();
        this.content = chatMessage.getContent();
        this.simpleTime = chatMessage.getTime().format(dateTimeFormatter);
    }
}
