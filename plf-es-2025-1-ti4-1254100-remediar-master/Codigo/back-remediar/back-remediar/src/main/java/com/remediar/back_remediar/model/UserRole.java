package com.remediar.back_remediar.model;

import lombok.Getter;

@Getter
public enum UserRole {

    ADMIN("admin"),

    FUNCIONARIO("funcionario"),

    USER("usuario");

    private final String role;

    UserRole(String role){
        this.role = role;
    }
}
