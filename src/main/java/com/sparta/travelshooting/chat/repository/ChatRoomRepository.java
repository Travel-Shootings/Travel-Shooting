package com.sparta.travelshooting.chat.repository;

import com.sparta.travelshooting.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
