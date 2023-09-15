package com.sparta.travelshooting.chat.service;

import com.sparta.travelshooting.chat.dto.ChatMessageResponseDto;
import com.sparta.travelshooting.chat.dto.ChatRoomResponseDto;
import com.sparta.travelshooting.chat.entity.ChatRoom;
import com.sparta.travelshooting.chat.repository.ChatMessageRepository;
import com.sparta.travelshooting.chat.repository.ChatMessageRepositoryQuery;
import com.sparta.travelshooting.chat.repository.ChatRoomRepository;
import com.sparta.travelshooting.common.ApiResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageRepositoryQuery chatMessageRepositoryQuery;

    // 관리자용 메서드, 현재 미사용
    @Override
    public ApiResponseDto createChatRoom(String chatRoomName) {
        chatRoomRepository.save(
                new ChatRoom(chatRoomName)
        );

        return new ApiResponseDto(chatRoomName + " 채팅방 개설 완료", HttpStatus.OK.value());
    }

    // 관리자용 메서드, 현재 미사용
    @Override
    @Transactional
    public ApiResponseDto deleteChatRoom(Long chatRoomId) {
        ChatRoom chatRoom = findChatRoom(chatRoomId);
        chatRoomRepository.delete(chatRoom);

        return new ApiResponseDto(chatRoom.getChatRoomName() + " 채팅방 삭제 완료", HttpStatus.OK.value());
    }

    @Override
    public List<ChatRoomResponseDto> getChatRoomList() {
        return chatRoomRepository.findAll().stream().map(ChatRoomResponseDto::new).toList();
    }

    // 관리자용 메서드, 현재 미사용
    @Override
    public List<ChatMessageResponseDto> getChatRoomChatMessage(Long chatRoomId) {
        return chatMessageRepository.findAllByChatRoomChatRoomIdOrderByTimeDesc(chatRoomId)
                .stream()
                .map(ChatMessageResponseDto::new)
                .toList();
    }

    @Override
    public List<ChatMessageResponseDto> getChatRoomChatMessagePaging(Long chatRoomId, Pageable pageable) {
        List<ChatMessageResponseDto> chatMessageResponseDtoList = chatMessageRepositoryQuery.getChatRoomChatMessagePaging(chatRoomId, pageable)
                .stream()
                .map(ChatMessageResponseDto::new)
                .toList();

        if (chatMessageResponseDtoList.isEmpty()) {
            throw new IndexOutOfBoundsException("조회할 데이터가 없습니다.");
        }

        return chatMessageResponseDtoList;
    }

    @Override
    public List<ChatMessageResponseDto> getChatRoomChatMessageReferenceValue(Long chatRoomId, Long chatMessageId, Long pageSize) {
        List<ChatMessageResponseDto> chatMessageResponseDtoList = chatMessageRepositoryQuery.getChatRoomChatMessageReferenceValue(chatRoomId, chatMessageId, pageSize)
                .stream()
                .map(ChatMessageResponseDto::new)
                .toList();

        if (chatMessageResponseDtoList.isEmpty()) {
            throw new IndexOutOfBoundsException("조회할 데이터가 없습니다.");
        }

        return chatMessageResponseDtoList;
    }

    @Override
    public ChatRoom findChatRoom(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId).orElseThrow(() ->
                new IllegalArgumentException("해당 채팅방은 존재하지 않습니다.")
        );
    }
}
