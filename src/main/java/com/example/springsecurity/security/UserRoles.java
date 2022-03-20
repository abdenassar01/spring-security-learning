package com.example.springsecurity.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.springsecurity.security.UserPermissions.*;

public enum UserRoles {
    ADMIN(Sets.newHashSet(STUDENT_READ, STUDENT_WRITE, COURSE_WRITE, COURSE_READ)),
    STUDENT(Sets.newHashSet()),
    MANAGER(Sets.newHashSet(STUDENT_READ, COURSE_READ));

    private final Set<UserPermissions> permissions;

    UserRoles(Set<UserPermissions> permissions) {
        this.permissions = permissions;
    }
    
    public Set<UserPermissions> getPermissions() {
        return permissions;
    }


    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
