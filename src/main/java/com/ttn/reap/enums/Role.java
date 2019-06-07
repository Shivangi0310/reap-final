package com.ttn.reap.enums;

public enum Role {

    USER("USER"),
    ADMIN("ADMIN"),
    SUPERVISOR("SUPERVISOR"),
    PRACTICE_HEAD("PRACTICE_HEAD");

    private String role;

    Role(String role) {
        this.role = role;
    }
}
