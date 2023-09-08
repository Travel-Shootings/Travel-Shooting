package com.sparta.travelshooting.post.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.travelshooting.post.entity.Post;
import com.sparta.travelshooting.post.entity.QPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getPostListWithId(long offset, Long id) {
        QPost post = QPost.post;

        return jpaQueryFactory.selectFrom(post)
                .offset(offset)
                .limit(id)
                .fetch();
    }
}
