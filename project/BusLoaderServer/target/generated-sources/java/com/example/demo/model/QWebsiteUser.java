package com.example.demo.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWebsiteUser is a Querydsl query type for WebsiteUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWebsiteUser extends EntityPathBase<WebsiteUser> {

    private static final long serialVersionUID = 1982691175L;

    public static final QWebsiteUser websiteUser = new QWebsiteUser("websiteUser");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QWebsiteUser(String variable) {
        super(WebsiteUser.class, forVariable(variable));
    }

    public QWebsiteUser(Path<? extends WebsiteUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWebsiteUser(PathMetadata metadata) {
        super(WebsiteUser.class, metadata);
    }

}

