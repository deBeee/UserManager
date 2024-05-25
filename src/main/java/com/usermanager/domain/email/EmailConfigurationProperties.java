package com.usermanager.domain.email;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "email")
public record EmailConfigurationProperties(
        String host,
        Integer port,
        String username,
        String password
) {
}
