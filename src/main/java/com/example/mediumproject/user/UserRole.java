package com.example.mediumproject.user;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    ROLE_PAID("ROLE_PAID"),
    ROLE_FREE("ROLE_FREE");

    UserRole(String value) {
        this.value = value;
    }

    private String value;
}
