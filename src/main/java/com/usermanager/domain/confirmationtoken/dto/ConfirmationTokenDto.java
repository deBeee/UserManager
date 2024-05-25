package com.usermanager.domain.confirmationtoken.dto;

import com.usermanager.domain.user.dto.UserDto;
import lombok.Builder;

@Builder
public record ConfirmationTokenDto(UserDto user, String value) {
}
