package com.usermanager.infrastructure.confirmationtoken.error.dto;

public record ConfirmationTokenNotFoundErrorResponse(int status, String message, String token) {
}
