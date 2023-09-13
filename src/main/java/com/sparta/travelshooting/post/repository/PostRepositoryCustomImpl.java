package com.sparta.travelshooting.post.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.travelshooting.post.entity.Post;
import com.sparta.travelshooting.post.entity.QPost;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Primary
@Repository
@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;

    @Override
    public List<Post> getPostsByPage(Pageable pageable) {
        QPost post = QPost.post;

        JPAQuery<Post> query = jpaQueryFactory.selectFrom(post)
                .from(post);

        query.offset(pageable.getOffset()); // Offset과 Limit을 직접 설정합니다.
        query.limit(pageable.getPageSize());

        return query.fetch();
    }
}
