package com.sparta.travelshooting.chat.controller;

import com.sparta.travelshooting.chat.dto.ChatMessageResponseDto;
import com.sparta.travelshooting.chat.dto.ChatRoomRequestDto;
import com.sparta.travelshooting.chat.service.ChatRoomService;
import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/chat-room")
@RequiredArgsConstructor
@Tag(name = "채팅방 관리 API")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @Operation(summary = "채팅방 개설")
    @PostMapping
    public ResponseEntity<ApiResponseDto> createChatRoom(@RequestBody ChatRoomRequestDto chatRoomRequestDto) {
        ApiResponseDto apiResponseDto = chatRoomService.createChatRoom(chatRoomRequestDto.getRoomName());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "채팅방 삭제")
    @DeleteMapping("/{chatRoomId}")
    public ResponseEntity<ApiResponseDto> deleteChatRoom(@PathVariable Long chatRoomId) {
        ApiResponseDto apiResponseDto = chatRoomService.deleteChatRoom(chatRoomId);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "채팅방 입장")
    @PostMapping("/{chatRoomId}/join")
    public ResponseEntity<ApiResponseDto> joinChatRoom(@PathVariable Long chatRoomId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto apiResponseDto = chatRoomService.joinChatRoom(userDetails.getUser().getId(), chatRoomId);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "채팅방 나가기")
    @PostMapping("/{chatRoomId}/leave")
    public ResponseEntity<ApiResponseDto> leaveChatRoom(@PathVariable Long chatRoomId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto apiResponseDto = chatRoomService.leaveChatRoom(userDetails.getUser().getId(), chatRoomId);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "채팅방 채팅 내역 전체 불러오기")
    @GetMapping("/{chatRoomId}")
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
}
