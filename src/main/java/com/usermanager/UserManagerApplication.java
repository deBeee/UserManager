package com.usermanager;

import com.usermanager.infrastructure.security.jwt.JwtConfigurationProperties;
import com.usermanager.domain.email.EmailConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = {EmailConfigurationProperties.class, JwtConfigurationProperties.class})
public class UserManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserManagerApplication.class, args);
    }

}
