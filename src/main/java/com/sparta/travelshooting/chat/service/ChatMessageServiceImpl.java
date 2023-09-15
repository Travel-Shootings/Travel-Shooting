package com.sparta.travelshooting.chat.service;

import com.sparta.travelshooting.chat.dto.ChatMessageRequestDto;
import com.sparta.travelshooting.chat.dto.ChatMessageResponseDto;
import com.sparta.travelshooting.chat.entity.ChatMessage;
import com.sparta.travelshooting.chat.entity.ChatRoom;
import com.sparta.travelshooting.chat.repository.ChatMessageRepository;
import com.sparta.travelshooting.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomService chatRoomService;

    @Override
    public ChatMessageResponseDto sendChat(Long chatRoomId, ChatMessageRequestDto chatMessageRequestDto, User user) {
        String content = chatMessageRequestDto.getContent();

        ChatRoom chatRoom = chatRoomService.findChatRoom(chatRoomId);

        ChatMessage chatMessage = ChatMessage.builder()
                .content(content)
                .chatRoom(chatRoom)
                .user(user)
                .build();

        chatMessageRepository.save(chatMessage);

        return new ChatMessageResponseDto(chatMessage);
    }
}
