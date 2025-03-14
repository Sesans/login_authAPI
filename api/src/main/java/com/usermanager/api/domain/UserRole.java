package com.usermanager.api.domain;

import lombok.Getter;

@Getter
public enum UserRole {
    USER("USER"), ADMIN("ADMIN");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }
}