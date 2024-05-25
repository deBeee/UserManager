package com.usermanager.infrastructure.user.controller.error.dto;

public record UserNotFoundErrorResponse(int status, String message) {
}
