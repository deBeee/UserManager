package com.usermanager.infrastructure.email;

import com.usermanager.domain.user.UserService;
import com.usermanager.infrastructure.email.controller.dto.EmailConfirmationResultDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class EmailController {

    private final UserService userService;

    @GetMapping("/confirm")
    public ResponseEntity<EmailConfirmationResultDto> confirmEmail(@RequestParam("token") String token) {
        boolean isConfirmed = userService.confirmEmail(token);
        if (isConfirmed) {
            return ResponseEntity.ok(new EmailConfirmationResultDto("Email confirmed", true));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new EmailConfirmationResultDto("Invalid or expired token", false));
        }
    }
}
