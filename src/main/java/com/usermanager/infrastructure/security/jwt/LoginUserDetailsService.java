package com.usermanager.infrastructure.security.jwt;


import com.usermanager.domain.user.UserService;
import com.usermanager.domain.user.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collections;


@AllArgsConstructor
public class LoginUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws BadCredentialsException {
        UserDto userDto = userService.findByUsername(username);
        return getUser(userDto);
    }

    private UserDetails getUser(UserDto user) {
        return User.builder()
                .username(user.username())
                .password(user.password())
                .authorities(Collections.emptyList())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!user.isEnabled())
                .build();
    }
}
