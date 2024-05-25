package com.usermanager.infrastructure.user.controller.error.exception;

import lombok.Getter;

@Getter
public class DuplicateUsernameException extends RuntimeException {

    private final String username;
    public DuplicateUsernameException(String message, String username) {
        super(message);
        this.username = username;
    }
}
