package com.example.springsecurity.security;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.example.springsecurity.security.UserPermissions.*;

public enum UserRoles {
    ADMIN(Sets.newHashSet(STUDENT_READ, STUDENT_WRITE, COURSE_WRITE, COURSE_READ)),
    STUDENT(Sets.newHashSet());

    private final Set<UserPermissions> permissions;

    UserRoles(Set<UserPermissions> permissions) {
        this.permissions = permissions;
    }
}
