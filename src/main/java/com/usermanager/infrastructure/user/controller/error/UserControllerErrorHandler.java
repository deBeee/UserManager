package com.usermanager.infrastructure.user.controller.error;

import com.usermanager.infrastructure.user.controller.UserController;
import com.usermanager.infrastructure.user.controller.error.dto.DuplicateEmailErrorResponse;
import com.usermanager.infrastructure.user.controller.error.dto.DuplicateUsernameErrorResponse;
import com.usermanager.infrastructure.user.controller.error.dto.UserNotFoundErrorResponse;
import com.usermanager.infrastructure.user.controller.error.exception.DuplicateEmailException;
import com.usermanager.infrastructure.user.controller.error.exception.DuplicateUsernameException;
import com.usermanager.infrastructure.user.controller.error.exception.UserNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = UserController.class)
@Log4j2
public class UserControllerErrorHandler {

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<DuplicateEmailErrorResponse> handleDuplicateEmailException(DuplicateEmailException exception) {
        log.warn("DuplicateEmailException thrown while registering new user");
        return ResponseEntity.badRequest()
                .body(new DuplicateEmailErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), exception.getEmail()));
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<DuplicateUsernameErrorResponse> handleDuplicateUsernameException(DuplicateUsernameException exception) {
        log.warn("DuplicateUsernameException thrown while registering new user");
        return ResponseEntity.badRequest()
                .body(new DuplicateUsernameErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), exception.getUsername()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<UserNotFoundErrorResponse> handleUserNotFoundException(UserNotFoundException exception) {
        log.warn("UserNotFoundException thrown");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new UserNotFoundErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage()));
    }
}
