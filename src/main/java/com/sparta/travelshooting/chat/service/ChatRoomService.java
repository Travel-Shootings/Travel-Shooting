package com.sparta.travelshooting.chat.service;

import com.sparta.travelshooting.chat.entity.ChatRoom;
import com.sparta.travelshooting.chat.entity.UserChatRoom;
import com.sparta.travelshooting.common.entity.ApiResponseDto;

public interface ChatRoomService {

    /**
     * 채팅방 개설
     *
     * @param chatRoomName 채팅방 이름
     * @return 요청 처리 결과
     */
    ApiResponseDto createChatRoom(String chatRoomName);

    /**
     * 채팅방 참가
     *
     * @param userId     참가를 하려는 유저 ID
     * @param chatRoomId 참가할 채팅방 ID
     * @return 요청 처리 결과
     */
    ApiResponseDto joinChatRoom(Long userId, Long chatRoomId);

    /**
     * 채팅방 나가기
     *
     * @param userId     나가려는 유저 ID
     * @param chatRoomId 나갈 채팅방 ID
     * @return 요청 처리 결과
     */
    ApiResponseDto leaveChatRoom(Long userId, Long chatRoomId);

    /**
     * 채팅방 찾기
     *
     * @param chatRoomId 찾을 채팅방 ID
     * @return 찾은 채팅방
     */
    ChatRoom findChatRoom(Long chatRoomId);

    /**
     * 유저가 참가한 채팅방 찾기
     *
     * @param userId     참가한 유저 ID
     * @param chatRoomId 참가한 채팅방 ID
     * @return 찾은, 유저의 채팅방 참여 정보
     */
    UserChatRoom findUserChatRoom(Long userId, Long chatRoomId);
}
