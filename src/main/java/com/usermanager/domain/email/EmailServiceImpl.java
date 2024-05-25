package com.usermanager.domain.email;

import com.usermanager.domain.confirmationtoken.ConfirmationToken;
import com.usermanager.domain.confirmationtoken.ConfirmationTokenService;
import com.usermanager.domain.user.User;
import com.usermanager.domain.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmailServiceImpl implements EmailService {

    private final Mailer mailer;

    @Value("${email.from}")
    private String emailFrom;

    @Override
    public void sendConfirmationEmail(String to, String subject, ConfirmationToken token) {
        var email = EmailBuilder
                .startingBlank()
                .withSubject(subject)
                .withHTMLText(generateHTMLEmailContent(token))
                .from(emailFrom)
                .to(to)
                .buildEmail();

        mailer
                .sendMail(email)
                .thenAccept(res -> log.info("Email has been sent"))
                .exceptionally(ex -> {
                    log.error("Email error: %s".formatted(ex.getMessage()));
                    return null;
                });
        System.out.println("Email sent to " + to);
    }

    private String generateHTMLEmailContent(ConfirmationToken token) {
        String verificationLink = "http://localhost:8080/confirm?token=" + token.getValue();

        return """
                 Dear %s,<br>
                 Please click this link to confirm your email address and complete setup:<br>
                 %s<br>
                 The link will expire after 10 minutes<br>
                """.formatted(token.getUser().getUsername(), verificationLink);
    }
}
