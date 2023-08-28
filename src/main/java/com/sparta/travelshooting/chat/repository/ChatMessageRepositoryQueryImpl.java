package com.sparta.travelshooting.chat.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.travelshooting.chat.entity.ChatMessage;
import com.sparta.travelshooting.chat.entity.QChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepositoryQueryImpl implements ChatMessageRepositoryQuery {

    private final JPAQueryFactory jpaQueryFactory;

    // 채팅 메세지 페이징으로 불러오기
    @Override
    public List<ChatMessage> getChatRoomChatMessagePaging(Long chatRoomId, Pageable pageable) {
        QChatMessage chatMessage = QChatMessage.chatMessage;
        OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(Order.DESC, chatMessage.time);

        return jpaQueryFactory.select(chatMessage)
                .from(chatMessage)
                .where(
                        chatMessage.chatRoom.chatRoomId.eq(chatRoomId)
                )
                .offset(pageable.getOffset()) // 0-based 로 다시 수정
                .limit(pageable.getPageSize())
                .orderBy(orderSpecifier)
                .fetch();
    }

    // 채팅 메세지 기준값(미만)으로 불러오기
    @Override
    public List<ChatMessage> getChatRoomChatMessageReferenceValue(Long chatRoomId, Long chatMessageId, Long pageSize) {
        QChatMessage chatMessage = QChatMessage.chatMessage;
        OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(Order.DESC, chatMessage.time);

        return jpaQueryFactory.select(chatMessage)
                .from(chatMessage)
                .where(
                        chatMessage.chatMessageId.lt(chatMessageId)
                                .and(chatMessage.chatRoom.chatRoomId.eq(chatRoomId))
                )
                .limit(pageSize)
                .orderBy(orderSpecifier)
                .fetch();
    }
}