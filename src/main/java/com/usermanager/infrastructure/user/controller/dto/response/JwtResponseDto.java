package com.usermanager.infrastructure.user.controller.dto.response;

import lombok.Builder;

@Builder
public record JwtResponseDto(
        String username,
        String token
) {
}
