package com.sparta.travelshooting.chat.service;

import com.sparta.travelshooting.chat.dto.ChatMessageRequestDto;
import com.sparta.travelshooting.chat.dto.ChatMessageResponseDto;
import com.sparta.travelshooting.user.entity.User;

public interface ChatMessageService {

    /**
     * 채팅 보내기
     *
     * @param chatRoomId            채팅을 보낼 채팅방 ID
     * @param chatMessageRequestDto 채팅 내용
     * @param user  채팅을 보내는 사용자
     * @return 보내기 처리 완료된 채팅
     */
    ChatMessageResponseDto sendChat(Long chatRoomId, ChatMessageRequestDto chatMessageRequestDto, User user);

}
