package com.sparta.travelshooting.chat.service;

import com.sparta.travelshooting.chat.dto.ChatMessageRequestDto;
import com.sparta.travelshooting.chat.dto.ChatMessageResponseDto;
import com.sparta.travelshooting.chat.entity.ChatMessage;
import com.sparta.travelshooting.chat.entity.ChatRoom;
import com.sparta.travelshooting.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomService chatRoomService;

    @Override
    public ChatMessageResponseDto sendChat(Long chatRoomId, ChatMessageRequestDto chatMessageRequestDto) {
        String senderName = chatMessageRequestDto.getSenderName();
        String content = chatMessageRequestDto.getContent();

        ChatRoom chatRoom = chatRoomService.findChatRoom(chatRoomId);

        ChatMessage chatMessage = ChatMessage.builder()
                .senderName(senderName)
                .content(content)
                .chatRoom(chatRoom)
                .build();

        chatMessageRepository.save(chatMessage);

        return new ChatMessageResponseDto(chatMessage);
    }
}
