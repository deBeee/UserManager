package com.usermanager.infrastructure.user.controller.dto.response;

public record RegistrationResultResponseDto(Long userId, String username, boolean isCreated, boolean isEnabled) {
}
