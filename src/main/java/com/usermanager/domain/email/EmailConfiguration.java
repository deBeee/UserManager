package com.usermanager.domain.email;

import lombok.AllArgsConstructor;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class EmailConfiguration {

    private final EmailConfigurationProperties properties;

    @Bean
    public Mailer mailer() {
        return MailerBuilder
                .withSMTPServer(
                        properties.host(),
                        properties.port(),
                        properties.username(),
                        properties.password())
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .async()
                .buildMailer();
    }
}
