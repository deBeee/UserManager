package com.usermanager.infrastructure.user.controller;

import com.usermanager.domain.user.UserService;
import com.usermanager.infrastructure.security.jwt.JwtAuthenticatorFacade;
import com.usermanager.infrastructure.user.controller.dto.request.RegisterUserRequestDto;
import com.usermanager.infrastructure.user.controller.dto.request.TokenRequestDto;
import com.usermanager.infrastructure.user.controller.dto.request.UserPartiallyUpdateRequestDto;
import com.usermanager.infrastructure.user.controller.dto.response.UserPartiallyUpdateResponseDto;
import com.usermanager.infrastructure.user.controller.dto.request.UserUpdateRequestDto;
import com.usermanager.infrastructure.user.controller.dto.response.JwtResponseDto;
import com.usermanager.infrastructure.user.controller.dto.response.RegistrationResultResponseDto;
import com.usermanager.infrastructure.user.controller.dto.response.UserUpdateResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtAuthenticatorFacade jwtAuthenticatorFacade;

    @PostMapping(path = "/register")
    public ResponseEntity<RegistrationResultResponseDto> register(@RequestBody @Valid RegisterUserRequestDto userDto) {
        RegistrationResultResponseDto registrationResultResponseDto = this.userService.register(userDto);
        return ResponseEntity.ok(registrationResultResponseDto);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<JwtResponseDto> authenticateAndGenerateToken(@RequestBody @Valid TokenRequestDto tokenRequest) {
        final JwtResponseDto jwtResponse = jwtAuthenticatorFacade.authenticateAndGenerateToken(tokenRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PutMapping(path = "/user/{id}")
    public ResponseEntity<UserUpdateResponseDto> updateUserById(@PathVariable Long id, @RequestBody @Valid UserUpdateRequestDto requestDto) {
        UserUpdateResponseDto updatedUser = this.userService.updateUserById(id, requestDto);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping(path = "/user/{id}")
    public ResponseEntity<UserPartiallyUpdateResponseDto> partiallyUpdateUserById(@PathVariable Long id, @RequestBody @Valid UserPartiallyUpdateRequestDto partiallyUpdateRequestDto){
        UserPartiallyUpdateResponseDto partiallyUpdatedUser = this.userService.partiallyUpdateUserById(id, partiallyUpdateRequestDto);
        return ResponseEntity.ok(partiallyUpdatedUser);
    }

    @DeleteMapping(path = "/user/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id){
        this.userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
