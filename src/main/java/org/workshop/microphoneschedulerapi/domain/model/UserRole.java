package org.workshop.microphoneschedulerapi.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum UserRole {
    DIRECTOR(Set.of(
            Permission.DIRECTOR_READ,
            Permission.DIRECTOR_CREATE,
            Permission.DIRECTOR_UPDATE,
            Permission.DIRECTOR_DELETE
    )),
    ADMINISTRATOR(Set.of(
            Permission.ADMIN_READ,
            Permission.ADMIN_CREATE,
            Permission.ADMIN_UPDATE,
            Permission.ADMIN_DELETE,
            Permission.DIRECTOR_READ,
            Permission.DIRECTOR_CREATE,
            Permission.DIRECTOR_UPDATE,
            Permission.DIRECTOR_DELETE,
            Permission.ACTOR_READ,
            Permission.ACTOR_CREATE,
            Permission.ACTOR_UPDATE,
            Permission.ACTOR_DELETE
    )),
    ACTOR(Set.of(
            Permission.ACTOR_READ,
            Permission.ACTOR_CREATE,
            Permission.ACTOR_UPDATE,
            Permission.ACTOR_DELETE
    ))
    ;

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getUserAuthorities() {
        var authorities = getPermissions()
                            .stream()
                            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                            .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }
}
