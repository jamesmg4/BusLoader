package com.example.demo.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBus is a Querydsl query type for Bus
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBus extends EntityPathBase<Bus> {

    private static final long serialVersionUID = -1128043551L;

    public static final QBus bus = new QBus("bus");

    public final NumberPath<Integer> busNumber = createNumber("busNumber", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Student, QStudent> students = this.<Student, QStudent>createList("students", Student.class, QStudent.class, PathInits.DIRECT2);

    public QBus(String variable) {
        super(Bus.class, forVariable(variable));
    }

    public QBus(Path<? extends Bus> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBus(PathMetadata metadata) {
        super(Bus.class, metadata);
    }

}

