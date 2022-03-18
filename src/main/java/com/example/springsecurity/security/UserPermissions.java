package com.example.springsecurity.security;

public enum UserPermissions {
    
    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write"),  
    COURSE_READ("course:read"),
    COURSE_WRITE("course:write");

    public String getPermission() {
        return permission;
    }

    private final String permission;

    UserPermissions(String permission) {
        this.permission = permission;
    }
}
