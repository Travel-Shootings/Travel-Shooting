package com.sparta.travelshooting.chat.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatMessageId;

    @Column(nullable = false)
    private String senderName;

    @Column(nullable = false)
    private String content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    // 불필요한 연관관계 미설정
    // senderName, 즉 사용자 닉네임은 변경이 가능하기 때문에 고유값을 저장
    private Long userId;

    @Builder
    public ChatMessage(String senderName, String content, ChatRoom chatRoom, Long userId) {
        this.senderName = senderName;
        this.content = content;
        this.time = LocalDateTime.now();
        this.chatRoom = chatRoom;
        this.userId = userId;
    }
}
