package com.sparta.travelshooting.reviewPost.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.travelshooting.reviewPost.dto.ReviewPostListResponseDto;
import com.sparta.travelshooting.reviewPost.entity.QReviewPost;
import com.sparta.travelshooting.reviewPost.entity.ReviewPost;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ReviewPostRepositoryQueryImpl implements ReviewPostRepositoryQuery{

    private final JPAQueryFactory queryFactory;

    //게시물 페이지 조회
    @Override
    public Page<ReviewPostListResponseDto> getPageReviewPosts(Pageable pageable) {
        QReviewPost reviewPost = QReviewPost.reviewPost;

        List<ReviewPost> reviewPosts = queryFactory
                .selectFrom(reviewPost)
                .orderBy(reviewPost.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(reviewPost).fetchCount();
        return new PageImpl<>(reviewPosts.stream().map(ReviewPostListResponseDto::new).collect(Collectors.toList()), pageable, total);
    }
}
