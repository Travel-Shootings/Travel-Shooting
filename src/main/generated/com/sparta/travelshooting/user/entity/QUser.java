package com.sparta.travelshooting.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1116813342L;

    public static final QUser user = new QUser("user");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickname = createString("nickname");

    public final ListPath<com.sparta.travelshooting.notification.entity.Notification, com.sparta.travelshooting.notification.entity.QNotification> notifications = this.<com.sparta.travelshooting.notification.entity.Notification, com.sparta.travelshooting.notification.entity.QNotification>createList("notifications", com.sparta.travelshooting.notification.entity.Notification.class, com.sparta.travelshooting.notification.entity.QNotification.class, PathInits.DIRECT2);

    public final StringPath password = createString("password");

    public final ListPath<com.sparta.travelshooting.post.entity.PostLike, com.sparta.travelshooting.post.entity.QPostLike> postLikes = this.<com.sparta.travelshooting.post.entity.PostLike, com.sparta.travelshooting.post.entity.QPostLike>createList("postLikes", com.sparta.travelshooting.post.entity.PostLike.class, com.sparta.travelshooting.post.entity.QPostLike.class, PathInits.DIRECT2);

    public final ListPath<com.sparta.travelshooting.post.entity.Post, com.sparta.travelshooting.post.entity.QPost> posts = this.<com.sparta.travelshooting.post.entity.Post, com.sparta.travelshooting.post.entity.QPost>createList("posts", com.sparta.travelshooting.post.entity.Post.class, com.sparta.travelshooting.post.entity.QPost.class, PathInits.DIRECT2);

    public final StringPath recentPassword = createString("recentPassword");

    public final EnumPath<RegionEnum> region = createEnum("region", RegionEnum.class);

    public final EnumPath<RoleEnum> role = createEnum("role", RoleEnum.class);

    public final StringPath username = createString("username");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

