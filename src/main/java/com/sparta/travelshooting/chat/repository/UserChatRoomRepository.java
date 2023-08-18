package com.sparta.travelshooting.chat.repository;

import com.sparta.travelshooting.chat.entity.UserChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserChatRoomRepository extends JpaRepository<UserChatRoom, Long> {
    Optional<UserChatRoom> findByUserUserIdAndChatRoomChatRoomId(Long userId, Long chatRoomId);
}
