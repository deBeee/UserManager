package com.usermanager.domain.confirmationtoken;

import com.usermanager.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private final String value = UUID.randomUUID().toString();

    private final LocalDateTime creationDate = LocalDateTime.now();

    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    public ConfirmationToken(User user){
        this.user = user;
    }

    public boolean isValid() {
        return this.creationDate.plusMinutes(10).isAfter(LocalDateTime.now());
    }
}
