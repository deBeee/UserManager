package com.usermanager.infrastructure.user.controller.error.dto;

public record DuplicateEmailErrorResponse(int status, String message, String email) {
}
