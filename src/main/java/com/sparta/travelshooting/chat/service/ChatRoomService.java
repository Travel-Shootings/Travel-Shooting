package com.sparta.travelshooting.chat.service;

import com.sparta.travelshooting.chat.dto.ChatMessageResponseDto;
import com.sparta.travelshooting.chat.dto.ChatRoomResponseDto;
import com.sparta.travelshooting.chat.entity.ChatRoom;
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
     * 채팅방 목록 불러오기
     *
     * @return 채팅방 목록
     */
    List<ChatRoomResponseDto> getChatRoomList();

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
     * 채팅방의 채팅 내역 기준값(미만)으로 불러오기: 기준 채팅 이전의 채팅을 불러오기 위한 메서드
     *
     * @param chatRoomId    채팅 내역을 불러올 채팅방 ID
     * @param chatMessageId 기준 채팅 메세지 ID
     * @param pageSize      조회할 크기
     * @return 채팅 내역
     */
    List<ChatMessageResponseDto> getChatRoomChatMessageReferenceValue(Long chatRoomId, Long chatMessageId, Long pageSize);

    /**
     * 채팅방 찾기
     *
     * @param chatRoomId 찾을 채팅방 ID
     * @return 찾은 채팅방
     */
    ChatRoom findChatRoom(Long chatRoomId);
}
