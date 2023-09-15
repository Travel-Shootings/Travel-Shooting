package com.sparta.travelshooting.post.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.travelshooting.post.dto.PostListResponseDto;
import com.sparta.travelshooting.post.entity.Post;
import com.sparta.travelshooting.post.entity.QPost;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<PostListResponseDto> getPostsByPage(Pageable pageable) {
        QPost post = QPost.post;

        List<Post> posts = jpaQueryFactory
                .selectFrom(post)
                .orderBy(post.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = jpaQueryFactory.selectFrom(post).fetchCount();
        return new PageImpl<>(posts.stream().map(PostListResponseDto::new).collect(Collectors.toList()), pageable, total);
    }
}
