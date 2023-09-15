package com.sparta.travelshooting.chat.controller;

import com.sparta.travelshooting.chat.dto.ChatMessageResponseDto;
import com.sparta.travelshooting.chat.dto.ChatRoomRequestDto;
import com.sparta.travelshooting.chat.dto.ChatRoomResponseDto;
import com.sparta.travelshooting.chat.service.ChatRoomService;
import com.sparta.travelshooting.common.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/chat-room")
@RequiredArgsConstructor
@Tag(name = "채팅방 관리 API")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    // 관리자용 메서드, 현재 미사용
    @Operation(summary = "채팅방 개설")
    @PostMapping
    public ResponseEntity<ApiResponseDto> createChatRoom(@RequestBody ChatRoomRequestDto chatRoomRequestDto) {
        ApiResponseDto apiResponseDto = chatRoomService.createChatRoom(chatRoomRequestDto.getRoomName());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    // 관리자용 메서드, 현재 미사용
    @Operation(summary = "채팅방 삭제")
    @DeleteMapping("/{chatRoomId}")
    public ResponseEntity<ApiResponseDto> deleteChatRoom(@PathVariable Long chatRoomId) {
        ApiResponseDto apiResponseDto = chatRoomService.deleteChatRoom(chatRoomId);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "채팅방 목록 불러오기")
    @GetMapping
    public ResponseEntity<List<ChatRoomResponseDto>> getChatRoomList() {
        List<ChatRoomResponseDto> chatRoomResponseDtoList = chatRoomService.getChatRoomList();
        return new ResponseEntity<>(chatRoomResponseDtoList, HttpStatus.OK);
    }

    // 관리자용 메서드, 현재 미사용
    @Operation(summary = "채팅방 채팅 내역 전체 불러오기")
    @GetMapping("/{chatRoomId}/all-chat")
    public ResponseEntity<List<ChatMessageResponseDto>> getChatRoomChatMessage(@PathVariable Long chatRoomId) {
        List<ChatMessageResponseDto> chatMessageResponseDtoList = chatRoomService.getChatRoomChatMessage(chatRoomId);
        return new ResponseEntity<>(chatMessageResponseDtoList, HttpStatus.OK);
    }

    @Operation(summary = "채팅방 채팅 내역 페이징으로 불러오기")
    @GetMapping("/{chatRoomId}/paging")
    public ResponseEntity<List<ChatMessageResponseDto>> getChatRoomChatMessagePaging(@PathVariable Long chatRoomId, Pageable pageable) {
        List<ChatMessageResponseDto> chatMessageResponseDtoList = chatRoomService.getChatRoomChatMessagePaging(chatRoomId, pageable);
        return new ResponseEntity<>(chatMessageResponseDtoList, HttpStatus.OK);
    }

    @Operation(summary = "채팅방 채팅 내역 기준값으로 불러오기")
    @GetMapping("/{chatRoomId}")
    public ResponseEntity<List<ChatMessageResponseDto>> getChatRoomChatMessageReferenceValue(@RequestParam Long chatMessageId, @RequestParam Long pageSize, @PathVariable Long chatRoomId) {
        List<ChatMessageResponseDto> chatMessageResponseDtoList = chatRoomService.getChatRoomChatMessageReferenceValue(chatRoomId, chatMessageId, pageSize);
        return new ResponseEntity<>(chatMessageResponseDtoList, HttpStatus.OK);
    }
}
