package com.usermanager.infrastructure.user.controller.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RegisterUserRequestDto(
        @NotNull(message = "username must not be null")
        @NotEmpty(message = "username must not be empty")
        String username,

        @NotNull(message = "password must not be null")
        @NotEmpty(message = "password must not be empty")
        String password,

        @NotNull(message = "email must not be null")
        @NotEmpty(message = "email must not be empty")
        @Email(message = "email syntax is incorrect")
        String email) {
}
