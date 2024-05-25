package com.usermanager.infrastructure.user.controller.dto.response;

public record UserPartiallyUpdateResponseDto(
        Long id,
        String username,
        String password,
        String email
) {
}
