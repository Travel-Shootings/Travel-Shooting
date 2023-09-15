package com.sparta.travelshooting.post.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = -1435567544L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPost post = new QPost("post");

    public final com.sparta.travelshooting.common.QTimestamped _super = new com.sparta.travelshooting.common.QTimestamped(this);

    public final ListPath<com.sparta.travelshooting.comment.entity.Comment, com.sparta.travelshooting.comment.entity.QComment> commentList = this.<com.sparta.travelshooting.comment.entity.Comment, com.sparta.travelshooting.comment.entity.QComment>createList("commentList", com.sparta.travelshooting.comment.entity.Comment.class, com.sparta.travelshooting.comment.entity.QComment.class, PathInits.DIRECT2);

    public final StringPath contents = createString("contents");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.sparta.travelshooting.journeylist.entity.JourneyList, com.sparta.travelshooting.journeylist.entity.QJourneyList> journeyLists = this.<com.sparta.travelshooting.journeylist.entity.JourneyList, com.sparta.travelshooting.journeylist.entity.QJourneyList>createList("journeyLists", com.sparta.travelshooting.journeylist.entity.JourneyList.class, com.sparta.travelshooting.journeylist.entity.QJourneyList.class, PathInits.DIRECT2);

    public final NumberPath<Integer> likeCounts = createNumber("likeCounts", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final ListPath<PostLike, QPostLike> postLikes = this.<PostLike, QPostLike>createList("postLikes", PostLike.class, QPostLike.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public final com.sparta.travelshooting.user.entity.QUser user;

    public QPost(String variable) {
        this(Post.class, forVariable(variable), INITS);
    }

    public QPost(Path<? extends Post> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPost(PathMetadata metadata, PathInits inits) {
        this(Post.class, metadata, inits);
    }

    public QPost(Class<? extends Post> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.sparta.travelshooting.user.entity.QUser(forProperty("user")) : null;
    }

}

