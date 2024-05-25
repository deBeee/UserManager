package com.usermanager.infrastructure.user.controller.dto.response;

public record UserUpdateResponseDto(
        Long id,
        String username,
        String password,
        String email
) {
}
