package com.usermanager.domain.email;

import com.usermanager.domain.confirmationtoken.ConfirmationToken;

public interface EmailService {
    void sendConfirmationEmail(String to, String subject, ConfirmationToken token);
}
