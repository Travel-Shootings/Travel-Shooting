package com.sparta.travelshooting.chat.dto;

import com.sparta.travelshooting.chat.entity.ChatRoom;
import lombok.Getter;

@Getter
public class ChatRoomResponseDto {

    private final Long chatRoomId;
    private final String chatRoomName;

    public ChatRoomResponseDto(ChatRoom chatRoom) {
        this.chatRoomId = chatRoom.getChatRoomId();
        this.chatRoomName = chatRoom.getChatRoomName();
    }
}
