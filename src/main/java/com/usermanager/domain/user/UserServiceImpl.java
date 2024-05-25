package com.usermanager.domain.user;

import com.usermanager.domain.confirmationtoken.ConfirmationToken;
import com.usermanager.domain.confirmationtoken.ConfirmationTokenService;
import com.usermanager.domain.email.EmailService;
import com.usermanager.domain.user.dto.UserDto;
import com.usermanager.infrastructure.user.controller.dto.request.RegisterUserRequestDto;
import com.usermanager.infrastructure.user.controller.dto.request.UserPartiallyUpdateRequestDto;
import com.usermanager.infrastructure.user.controller.dto.request.UserUpdateRequestDto;
import com.usermanager.infrastructure.user.controller.dto.response.RegistrationResultResponseDto;
import com.usermanager.infrastructure.user.controller.dto.response.UserPartiallyUpdateResponseDto;
import com.usermanager.infrastructure.user.controller.dto.response.UserUpdateResponseDto;
import com.usermanager.infrastructure.user.controller.error.exception.DuplicateEmailException;
import com.usermanager.infrastructure.user.controller.error.exception.DuplicateUsernameException;
import com.usermanager.infrastructure.user.controller.error.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.usermanager.domain.user.UserMapper.mapUserDtoToUser;
import static com.usermanager.domain.user.UserMapper.mapUserRequestDtoToUser;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ConfirmationTokenService confirmationTokenService;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public RegistrationResultResponseDto register(RegisterUserRequestDto userDto) {
        if (userRepository.existsByEmail(userDto.email())) {
            throw new DuplicateEmailException("Error: Email %s is already in use!".formatted(userDto.email()), userDto.email());
        }
        if (userRepository.existsByUsername(userDto.username())) {
            throw new DuplicateUsernameException("Error: Username %s is already in use!".formatted(userDto.username()), userDto.username());
        }

        //creating user with encoded password from request payload data and saving in database
        String encodedPassword = bCryptPasswordEncoder.encode(userDto.password());
        User user = mapUserDtoToUser(new UserDto(userDto.username(), encodedPassword, userDto.email(), false));
        User savedUser = userRepository.save(user);

        //creating confirmation token for user
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenService.save(confirmationToken);

        //sending confirmation email
        emailService.sendConfirmationEmail(user.getEmail(), "Confirm your email address", confirmationToken);

        return new RegistrationResultResponseDto(savedUser.getId(), savedUser.getUsername(), true, false);
    }

    @Override
    @Transactional
    public boolean confirmEmail(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.findByValue(token);
        User user = confirmationToken.getUser();

        if (!user.isEnabled() && confirmationToken.isValid()) {
            //user token is valid and email is confirmed successfully
            user.setEnabled(true);
            userRepository.save(user);
            return true;
        } else {
            // if user confirmation token is invalid we have to delete user and his token accordingly
            // this allows user to register once again without DuplicateEmailException or DuplicateUsernameException being thrown
            confirmationTokenService.deleteById(confirmationToken.getId());
            userRepository.deleteById(user.getId());
            return false;
        }
    }

    @Override
    public UserDto findByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .map(user -> new UserDto(user.getUsername(), user.getPassword(), user.getEmail(), user.isEnabled()))
                .orElseThrow(() -> new BadCredentialsException(
                        "User with username '%s' not found".formatted(username)));
    }

    @Override
    @Transactional
    public UserUpdateResponseDto updateUserById(Long id, UserUpdateRequestDto requestDto) { // without hibernate dirty checking
        if (!this.userRepository.existsById(id)) {
            throw new UserNotFoundException("User with id = %s not found".formatted(id));
        }
        String encodedPassword = bCryptPasswordEncoder.encode(requestDto.password());
        User userFromRequest = mapUserRequestDtoToUser(new UserUpdateRequestDto(requestDto.username(), encodedPassword, requestDto.email()));
        userFromRequest.setEnabled(false);
        userFromRequest.setId(id);
        this.userRepository.updateById(id, userFromRequest);
        performChangingUserEmail(userFromRequest);
        return new UserUpdateResponseDto(id, userFromRequest.getUsername(), userFromRequest.getPassword(), userFromRequest.getEmail());
    }

    @Override
    @Transactional
    public UserPartiallyUpdateResponseDto partiallyUpdateUserById(Long id, UserPartiallyUpdateRequestDto requestDto) { // with hibernate dirty checking
        User userFromDb = this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id = %s not found".formatted(id)));
        if (requestDto.username() != null) {
            userFromDb.setUsername(requestDto.username());
        }
        if (requestDto.password() != null) {
            String encodedPassword = bCryptPasswordEncoder.encode(requestDto.password());
            userFromDb.setPassword(encodedPassword);
        }
        if (requestDto.email() != null) {
            userFromDb.setEmail(requestDto.email());
            userFromDb.setEnabled(false);
            performChangingUserEmail(userFromDb);
        }
        //we dont have to save userFromDb object since changes on this object are automatically flushed to database when transaction reaches COMMIT command (this method ends)
        return new UserPartiallyUpdateResponseDto(userFromDb.getId(), userFromDb.getUsername(), userFromDb.getPassword(), userFromDb.getEmail());
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        if (!this.userRepository.existsById(id)) {
            throw new UserNotFoundException("User with id = %s not found".formatted(id));
        }
        confirmationTokenService.deleteByUserId(id);
        userRepository.deleteById(id);
    }

    private void performChangingUserEmail(User user){
        // if user changes email he has to confirm this new email to be able to login,
        // therefore we are setting his isEnabled field to false, deleting old confirmation token,
        // generating new one and sending email with this new token
        this.confirmationTokenService.deleteByUserId(user.getId());
        ConfirmationToken newConfirmationToken = new ConfirmationToken(user);
        confirmationTokenService.save(newConfirmationToken);
        this.emailService.sendConfirmationEmail(user.getEmail(), "Confirm your new email address", newConfirmationToken);
    }
}