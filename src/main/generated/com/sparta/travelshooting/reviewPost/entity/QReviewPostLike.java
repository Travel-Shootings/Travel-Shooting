package com.sparta.travelshooting.reviewPost.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReviewPostLike is a Querydsl query type for ReviewPostLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewPostLike extends EntityPathBase<ReviewPostLike> {

    private static final long serialVersionUID = 83122351L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReviewPostLike reviewPostLike = new QReviewPostLike("reviewPostLike");

    public final com.sparta.travelshooting.common.QTimestamped _super = new com.sparta.travelshooting.common.QTimestamped(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final QReviewPost reviewPost;

    public final com.sparta.travelshooting.user.entity.QUser user;

    public QReviewPostLike(String variable) {
        this(ReviewPostLike.class, forVariable(variable), INITS);
    }

    public QReviewPostLike(Path<? extends ReviewPostLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReviewPostLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReviewPostLike(PathMetadata metadata, PathInits inits) {
        this(ReviewPostLike.class, metadata, inits);
    }

    public QReviewPostLike(Class<? extends ReviewPostLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.reviewPost = inits.isInitialized("reviewPost") ? new QReviewPost(forProperty("reviewPost"), inits.get("reviewPost")) : null;
        this.user = inits.isInitialized("user") ? new com.sparta.travelshooting.user.entity.QUser(forProperty("user")) : null;
    }

}

