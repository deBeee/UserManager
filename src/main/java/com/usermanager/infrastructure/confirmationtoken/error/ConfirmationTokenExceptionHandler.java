package com.usermanager.infrastructure.confirmationtoken.error;

import com.usermanager.domain.confirmationtoken.ConfirmationTokenService;
import com.usermanager.infrastructure.confirmationtoken.error.dto.ConfirmationTokenNotFoundErrorResponse;
import com.usermanager.infrastructure.confirmationtoken.error.exception.ConfirmationTokenNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = ConfirmationTokenService.class)
@Log4j2
public class ConfirmationTokenExceptionHandler {

    @ExceptionHandler(ConfirmationTokenNotFoundException.class)
    public ResponseEntity<ConfirmationTokenNotFoundErrorResponse> handleConfirmationTokenNotFoundException(ConfirmationTokenNotFoundException exception){
        log.warn("ConfirmationTokenNotFoundException thrown while confirming user email");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ConfirmationTokenNotFoundErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage(), exception.getValue()));
    }
}
