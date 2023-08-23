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
                .offset(pageable.getOffset() - 1) // 1-based
                .limit(pageable.getPageSize())
                .orderBy(orderSpecifier)
                .fetch();
    }
}
