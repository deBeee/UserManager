package com.usermanager.domain.user;

import com.usermanager.domain.user.dto.UserDto;
import com.usermanager.infrastructure.user.controller.dto.request.RegisterUserRequestDto;
import com.usermanager.infrastructure.user.controller.dto.request.UserUpdateRequestDto;

public interface UserMapper {
    static User mapUserDtoToUser(UserDto userDto) {
        return User.builder()
                .username(userDto.username())
                .password(userDto.password())
                .email(userDto.email())
                .isEnabled(userDto.isEnabled())
                .build();
    }

    static User mapRegisterUserDtoToUser(RegisterUserRequestDto registerUserRequestDto) {
        return User.builder()
                .username(registerUserRequestDto.username())
                .password(registerUserRequestDto.password())
                .email(registerUserRequestDto.email())
                .build();
    }

    static User mapUserRequestDtoToUser(UserUpdateRequestDto userRequestDto) {
        return User.builder()
                .username(userRequestDto.username())
                .password(userRequestDto.password())
                .email(userRequestDto.email())
                .build();
    }

    static UserDto mapUserToUserDto(User user) {
        return new UserDto(
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.isEnabled()
        );
    }
}
