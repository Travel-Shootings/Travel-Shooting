package com.sparta.travelshooting.chat.controller;

import com.sparta.travelshooting.chat.dto.ChatMessageRequestDto;
import com.sparta.travelshooting.chat.dto.ChatMessageResponseDto;
import com.sparta.travelshooting.chat.service.ChatMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "채팅 관리 API")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @Operation(summary = "채팅 보내기")
    @MessageMapping("/hello/{chatRoomId}")
    @SendTo("/topic/messages/{chatRoomId}")
    public ChatMessageResponseDto sendChat(@DestinationVariable Long chatRoomId, ChatMessageRequestDto chatMessageRequestDto) {
        return chatMessageService.sendChat(chatRoomId, chatMessageRequestDto);
    }
}
