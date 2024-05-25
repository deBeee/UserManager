package com.usermanager.domain.user.dto;


public record UserDto(
        String username,
        String password,
        String email,
        boolean isEnabled
) {
}
