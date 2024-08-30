package com.example.demo.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAttendee is a Querydsl query type for Attendee
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAttendee extends EntityPathBase<Attendee> {

    private static final long serialVersionUID = 1179259897L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAttendee attendee = new QAttendee("attendee");

    public final BooleanPath absent = createBoolean("absent");

    public final QEvent event;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath onBus = createBoolean("onBus");

    public final QStudent student;

    public QAttendee(String variable) {
        this(Attendee.class, forVariable(variable), INITS);
    }

    public QAttendee(Path<? extends Attendee> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAttendee(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAttendee(PathMetadata metadata, PathInits inits) {
        this(Attendee.class, metadata, inits);
    }

    public QAttendee(Class<? extends Attendee> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.event = inits.isInitialized("event") ? new QEvent(forProperty("event")) : null;
        this.student = inits.isInitialized("student") ? new QStudent(forProperty("student"), inits.get("student")) : null;
    }

}

