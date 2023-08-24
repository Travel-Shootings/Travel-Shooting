package com.sparta.travelshooting.chat.repository;

import com.sparta.travelshooting.chat.entity.ChatMessage;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChatMessageRepositoryQuery {
    List<ChatMessage> getChatRoomChatMessagePaging(Long chatRoomId, Pageable pageable);
}
