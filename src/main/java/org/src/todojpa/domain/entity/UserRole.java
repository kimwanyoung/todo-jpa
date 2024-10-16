package org.src.todojpa.domain.entity;

public enum UserRole {
    ADMIN("ADMIN"),
    USER("USER");

    private final String role;

    UserRole(String authority) {
        this.role = authority;
    }

    public static UserRole from(String authority) {
        for(UserRole userRole : UserRole.values()) {
            if(authority.equals(userRole.role)) return userRole;
        }

        throw new IllegalArgumentException("잘못된 권한입니다.");
    }

    public boolean isAdmin() {
        return this == ADMIN;
    }
}
