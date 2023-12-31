package com.sparta.travelshooting.chat.controller;

import com.sparta.travelshooting.chat.dto.ChatMessageRequestDto;
import com.sparta.travelshooting.chat.dto.ChatMessageResponseDto;
import com.sparta.travelshooting.chat.service.ChatMessageService;
import com.sparta.travelshooting.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Tag(name = "채팅 관리 API")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @Operation(summary = "채팅 보내기")
    @MessageMapping("/message/{chatRoomId}")
    @SendTo("/sub/chat/room/{chatRoomId}")
    public ResponseEntity<ChatMessageResponseDto> sendChat(@DestinationVariable Long chatRoomId,
                                                           ChatMessageRequestDto chatMessageRequestDto,
                                                           StompHeaderAccessor headerAccessor) {

        // 세션 연결을 할 때 저장했던 user를 불러옴
        User user = (User) headerAccessor.getSessionAttributes().get("user");

        ChatMessageResponseDto chatMessageResponseDto = chatMessageService.sendChat(chatRoomId, chatMessageRequestDto, user);
        return new ResponseEntity<>(chatMessageResponseDto, HttpStatus.OK);
    }
}
