package com.sparta.travelshooting.chat.service;

import com.sparta.travelshooting.chat.entity.ChatRoom;
import com.sparta.travelshooting.chat.entity.UserChatRoom;
import com.sparta.travelshooting.chat.repository.ChatRoomRepository;
import com.sparta.travelshooting.chat.repository.UserChatRoomRepository;
import com.sparta.travelshooting.common.entity.ApiResponseDto;
import com.sparta.travelshooting.user.entity.User;
import com.sparta.travelshooting.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
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
    public ApiResponseDto joinChatRoom(Long userId, Long chatRoomId) {
        User user = findUser(userId);
        ChatRoom chatRoom = findChatRoom(chatRoomId);
        userChatRoomRepository.save(
                new UserChatRoom(user, chatRoom)
        );

        return new ApiResponseDto(user.getNickname() + "님이 " + chatRoom.getChatRoomName() + " 채팅방 참여 완료", HttpStatus.OK.value());
    }

    @Override
    @Transactional
    public ApiResponseDto leaveChatRoom(Long chatRoomId, Long userId) {
        User user = findUser(userId);
        ChatRoom chatRoom = findChatRoom(chatRoomId);
        UserChatRoom userChatRoom = findUserChatRoom(userId, chatRoomId);
        userChatRoomRepository.delete(userChatRoom);

        return new ApiResponseDto(user.getNickname() + "님이 " + chatRoom.getChatRoomName() + " 채팅방 나기기 완료", HttpStatus.OK.value());
    }

    @Override
    public ChatRoom findChatRoom(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId).orElseThrow(() ->
                new IllegalArgumentException("해당 채팅방은 존재하지 않습니다.")
        );
    }

    @Override
    public UserChatRoom findUserChatRoom(Long userId, Long chatRoomId) {
        return userChatRoomRepository.findByUserUserIdAndChatRoomChatRoomId(userId, chatRoomId).orElseThrow(() ->
                new IllegalArgumentException("채팅방 참여 정보가 잘못되었습니다.")
        );
    }

    // User 기능 pr 이전 임시 메서드
    public User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("해당 유저는 존재하지 않습니다.")
        );
    }
}
