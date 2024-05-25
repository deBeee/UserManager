package com.usermanager.infrastructure.user.controller.dto.request;

import jakarta.validation.constraints.Email;

public record UserPartiallyUpdateRequestDto(
        String username,
        String password,
        @Email
        String email
) {
}
