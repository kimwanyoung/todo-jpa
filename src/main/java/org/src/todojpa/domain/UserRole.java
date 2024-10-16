package org.src.todojpa.domain;

public enum UserRole {
    ADMIN("ADMIN"),
    USER("USER");

    private final String authority;

    UserRole(String authority) {
        this.authority = authority;
    }

    public static UserRole from(String authority) {
        for(UserRole role : UserRole.values()) {
            if(authority.equals(role.authority)) return role;
        }

        throw new IllegalArgumentException("잘못된 권한입니다.");
    }

    public boolean isAdmin() {
        return this == ADMIN;
    }
}
