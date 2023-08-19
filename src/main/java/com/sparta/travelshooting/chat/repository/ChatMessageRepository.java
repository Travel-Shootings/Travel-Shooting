package com.sparta.travelshooting.chat.repository;

import com.sparta.travelshooting.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByChatRoomChatRoomId(Long chatRoomId);
}
