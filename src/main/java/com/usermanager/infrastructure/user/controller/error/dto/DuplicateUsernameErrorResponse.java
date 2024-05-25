package com.usermanager.infrastructure.user.controller.error.dto;

public record DuplicateUsernameErrorResponse(int status, String message, String username) {
}
