package com.sparta.travelshooting.chat.dto;

import com.sparta.travelshooting.chat.entity.ChatMessage;
import com.sparta.travelshooting.common.service.SimpleLocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatMessageResponseDto {
    private final String senderName;
    private final String content;
    private final String simpleTime;
    private final Long chatMessageId;

    @Builder
    public ChatMessageResponseDto(ChatMessage chatMessage) {
        this.senderName = chatMessage.getSenderName();
        this.content = chatMessage.getContent();
        this.simpleTime = SimpleLocalDateTime.dateTimeFormat(chatMessage.getTime());
        this.chatMessageId = chatMessage.getChatMessageId();
    }
}
