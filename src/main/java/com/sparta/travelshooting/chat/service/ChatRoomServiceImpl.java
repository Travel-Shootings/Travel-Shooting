package com.sparta.travelshooting.chat.service;

import com.sparta.travelshooting.chat.dto.ChatMessageResponseDto;
import com.sparta.travelshooting.chat.dto.ChatRoomResponseDto;
import com.sparta.travelshooting.chat.entity.ChatRoom;
import com.sparta.travelshooting.chat.entity.UserChatRoom;
import com.sparta.travelshooting.chat.repository.ChatMessageRepository;
import com.sparta.travelshooting.chat.repository.ChatMessageRepositoryQuery;
import com.sparta.travelshooting.chat.repository.ChatRoomRepository;
import com.sparta.travelshooting.chat.repository.UserChatRoomRepository;
import com.sparta.travelshooting.common.ApiResponseDto;
import com.sparta.travelshooting.user.entity.User;
import com.sparta.travelshooting.user.repository.UserRepository;
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
    private final UserChatRoomRepository userChatRoomRepository;
    private final UserRepository userRepository;

    @Override
    public ApiResponseDto createChatRoom(String chatRoomName) {
        chatRoomRepository.save(
                new ChatRoom(chatRoomName)
        );

        return new ApiResponseDto(chatRoomName + " 채팅방 개설 완료", HttpStatus.OK.value());
    }

    @Override
    @Transactional
    public ApiResponseDto deleteChatRoom(Long chatRoomId) {
        ChatRoom chatRoom = findChatRoom(chatRoomId);
        chatRoomRepository.delete(chatRoom);

        return new ApiResponseDto(chatRoom.getChatRoomName() + " 채팅방 삭제 완료", HttpStatus.OK.value());
    }

    @Override
    public ApiResponseDto joinChatRoom(Long userId, Long chatRoomId) {
        try {
            findUserChatRoom(userId, chatRoomId);
            return new ApiResponseDto("이미 채팅방에 입장하셨습니다.", HttpStatus.BAD_REQUEST.value());
        } catch (IllegalArgumentException e) {
            User user = findUser(userId);
            ChatRoom chatRoom = findChatRoom(chatRoomId);
            userChatRoomRepository.save(
                    new UserChatRoom(user, chatRoom)
            );
            return new ApiResponseDto(user.getNickname() + "님이 " + chatRoom.getChatRoomName() + " 채팅방 참여 완료", HttpStatus.OK.value());
        }
    }

    @Override
    @Transactional
    public ApiResponseDto leaveChatRoom(Long userId, Long chatRoomId) {
        User user = findUser(userId);
        ChatRoom chatRoom = findChatRoom(chatRoomId);
        UserChatRoom userChatRoom = findUserChatRoom(userId, chatRoomId);
        userChatRoomRepository.delete(userChatRoom);

        return new ApiResponseDto(user.getNickname() + "님이 " + chatRoom.getChatRoomName() + " 채팅방 나기기 완료", HttpStatus.OK.value());
    }

    @Override
    public List<ChatRoomResponseDto> getChatRoomList() {
        return chatRoomRepository.findAll().stream().map(ChatRoomResponseDto::new).toList();
    }

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

    @Override
    public UserChatRoom findUserChatRoom(Long userId, Long chatRoomId) {
        return userChatRoomRepository.findByUserIdAndChatRoomChatRoomId(userId, chatRoomId).orElseThrow(() ->
                new IllegalArgumentException("채팅방 입장 정보가 잘못되었습니다.")
        );
    }

    // User 기능 pr 이전 임시 메서드
    public User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("해당 유저는 존재하지 않습니다.")
        );
    }
}
