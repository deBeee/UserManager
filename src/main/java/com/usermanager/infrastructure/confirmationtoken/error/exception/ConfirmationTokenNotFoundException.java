package com.usermanager.infrastructure.confirmationtoken.error.exception;

import lombok.Getter;

@Getter
public class ConfirmationTokenNotFoundException extends RuntimeException{

    private final String value;
    public ConfirmationTokenNotFoundException(String message, String value) {
        super(message);
        this.value = value;
    }
}
