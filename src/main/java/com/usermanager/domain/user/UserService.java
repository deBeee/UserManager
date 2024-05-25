package com.usermanager.domain.user;

import com.usermanager.infrastructure.user.controller.dto.request.RegisterUserRequestDto;
import com.usermanager.infrastructure.user.controller.dto.request.UserPartiallyUpdateRequestDto;
import com.usermanager.infrastructure.user.controller.dto.request.UserUpdateRequestDto;
import com.usermanager.infrastructure.user.controller.dto.response.RegistrationResultResponseDto;
import com.usermanager.domain.user.dto.UserDto;
import com.usermanager.infrastructure.user.controller.dto.response.UserPartiallyUpdateResponseDto;
import com.usermanager.infrastructure.user.controller.dto.response.UserUpdateResponseDto;


public interface UserService {
    RegistrationResultResponseDto register(RegisterUserRequestDto userDto);
    boolean confirmEmail(String token);
    UserDto findByUsername(String username);
    UserUpdateResponseDto updateUserById(Long id, UserUpdateRequestDto requestDto);
    UserPartiallyUpdateResponseDto partiallyUpdateUserById(Long id, UserPartiallyUpdateRequestDto partiallyUpdateRequestDto);
    void deleteUserById(Long id);
}
