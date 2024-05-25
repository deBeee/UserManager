package com.usermanager.infrastructure.user.controller.error.exception;

import lombok.Getter;

@Getter
public class DuplicateEmailException extends RuntimeException {

    private final String email;
    public DuplicateEmailException(String message, String email) {
        super(message);
        this.email = email;
    }
}
