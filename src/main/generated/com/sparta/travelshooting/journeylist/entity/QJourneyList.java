package com.sparta.travelshooting.journeylist.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QJourneyList is a Querydsl query type for JourneyList
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJourneyList extends EntityPathBase<JourneyList> {

    private static final long serialVersionUID = -489554482L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QJourneyList journeyList = new QJourneyList("journeyList");

    public final NumberPath<Long> budget = createNumber("budget", Long.class);

    public final DateTimePath<java.time.LocalDateTime> endJourney = createDateTime("endJourney", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath locations = createString("locations");

    public final NumberPath<Integer> members = createNumber("members", Integer.class);

    public final StringPath placeAddress = createString("placeAddress");

    public final com.sparta.travelshooting.post.entity.QPost post;

    public final DateTimePath<java.time.LocalDateTime> startJourney = createDateTime("startJourney", java.time.LocalDateTime.class);

    public QJourneyList(String variable) {
        this(JourneyList.class, forVariable(variable), INITS);
    }

    public QJourneyList(Path<? extends JourneyList> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QJourneyList(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QJourneyList(PathMetadata metadata, PathInits inits) {
        this(JourneyList.class, metadata, inits);
    }

    public QJourneyList(Class<? extends JourneyList> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new com.sparta.travelshooting.post.entity.QPost(forProperty("post"), inits.get("post")) : null;
    }

}

