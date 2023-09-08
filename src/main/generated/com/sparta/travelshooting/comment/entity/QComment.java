package com.sparta.travelshooting.comment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QComment is a Querydsl query type for Comment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QComment extends EntityPathBase<Comment> {

    private static final long serialVersionUID = 295238926L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QComment comment = new QComment("comment");

    public final com.sparta.travelshooting.common.QTimestamped _super = new com.sparta.travelshooting.common.QTimestamped(this);

    public final NumberPath<Long> commentId = createNumber("commentId", Long.class);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath nickName = createString("nickName");

    public final com.sparta.travelshooting.post.entity.QPost post;

    public final ListPath<com.sparta.travelshooting.reply.entity.Reply, com.sparta.travelshooting.reply.entity.QReply> replyList = this.<com.sparta.travelshooting.reply.entity.Reply, com.sparta.travelshooting.reply.entity.QReply>createList("replyList", com.sparta.travelshooting.reply.entity.Reply.class, com.sparta.travelshooting.reply.entity.QReply.class, PathInits.DIRECT2);

    public final com.sparta.travelshooting.reviewPost.entity.QReviewPost reviewPost;

    public final com.sparta.travelshooting.user.entity.QUser user;

    public QComment(String variable) {
        this(Comment.class, forVariable(variable), INITS);
    }

    public QComment(Path<? extends Comment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QComment(PathMetadata metadata, PathInits inits) {
        this(Comment.class, metadata, inits);
    }

    public QComment(Class<? extends Comment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new com.sparta.travelshooting.post.entity.QPost(forProperty("post"), inits.get("post")) : null;
        this.reviewPost = inits.isInitialized("reviewPost") ? new com.sparta.travelshooting.reviewPost.entity.QReviewPost(forProperty("reviewPost"), inits.get("reviewPost")) : null;
        this.user = inits.isInitialized("user") ? new com.sparta.travelshooting.user.entity.QUser(forProperty("user")) : null;
    }

}

