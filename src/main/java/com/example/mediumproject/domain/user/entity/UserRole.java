package com.example.mediumproject.domain.user.entity;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    ROLE_PAID("ROLE_PAID");

    UserRole(String value) {
        this.value = value;
    }

    private String value;
}
