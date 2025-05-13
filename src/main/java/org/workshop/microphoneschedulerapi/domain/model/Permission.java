package org.workshop.microphoneschedulerapi.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_CREATE("admin:create"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete"),

    DIRECTOR_READ("director:read"),
    DIRECTOR_CREATE("director:create"),
    DIRECTOR_UPDATE("director:update"),
    DIRECTOR_DELETE("director:delete"),

    ACTOR_READ("actor:read"),
    ACTOR_CREATE("actor:create"),
    ACTOR_UPDATE("actor:update"),
    ACTOR_DELETE("actor:delete")
    ;

    @Getter
    private final String permission;
}
