package com.sparta.travelshooting.chat.controller;

import com.sparta.travelshooting.chat.dto.ChatRoomRequestDto;
import com.sparta.travelshooting.chat.service.ChatRoomService;
import com.sparta.travelshooting.common.entity.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat-room")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping
    public ApiResponseDto createChatRoom(@RequestBody ChatRoomRequestDto chatRoomRequestDto) {
        return chatRoomService.createChatRoom(chatRoomRequestDto.getRoomName());
    }

    @PostMapping("/{chatRoomId}")
    public ApiResponseDto joinChatRoom(@PathVariable Long chatRoomId) {
        return chatRoomService.joinChatRoom(1L, chatRoomId);
    }

    @DeleteMapping("/{chatRoomId}")
    public ApiResponseDto leaveChatRoom(@PathVariable Long chatRoomId) {
        return chatRoomService.leaveChatRoom(1L, chatRoomId);
    }
}
