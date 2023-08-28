package com.sparta.travelshooting.chat.service;

import com.sparta.travelshooting.chat.dto.ChatMessageResponseDto;
import com.sparta.travelshooting.chat.entity.ChatRoom;
import com.sparta.travelshooting.chat.entity.UserChatRoom;
import com.sparta.travelshooting.common.ApiResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChatRoomService {

    /**
     * 채팅방 개설
     *
     * @param chatRoomName 채팅방 이름
     * @return 요청 처리 결과
     */
    ApiResponseDto createChatRoom(String chatRoomName);

    /**
     * 채팅방 삭제
     *
     * @param chatRoomId 삭제할 채팅방 ID
     * @return 요청 처리 결과
     */
    ApiResponseDto deleteChatRoom(Long chatRoomId);

    /**
     * 채팅방 입장
     *
     * @param userId     입장를 하려는 유저 ID
     * @param chatRoomId 입장할 채팅방 ID
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
     * 채팅방의 채팅 내역 전체 불러오기
     *
     * @param chatRoomId 채팅 내역을 불러올 채팅방 ID
     * @return 채팅 내역
     */
    List<ChatMessageResponseDto> getChatRoomChatMessage(Long chatRoomId);

    /**
     * 채팅방의 채팅 내역 페이징으로 불러오기
     *
     * @param chatRoomId 채팅 내역을 불러올 채팅방 ID
     * @param pageable   페이징 정보
     * @return 채팅 내역
     */
    List<ChatMessageResponseDto> getChatRoomChatMessagePaging(Long chatRoomId, Pageable pageable);

    /**
     * 채팅방 찾기
     *
     * @param chatRoomId 찾을 채팅방 ID
     * @return 찾은 채팅방
     */
    ChatRoom findChatRoom(Long chatRoomId);

    /**
     * 유저가 입장한 채팅방 찾기
     *
     * @param userId     입장한 유저 ID
     * @param chatRoomId 입장한 채팅방 ID
     * @return 찾은, 유저의 채팅방 참여 정보
     */
    UserChatRoom findUserChatRoom(Long userId, Long chatRoomId);
}
