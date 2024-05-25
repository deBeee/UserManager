package com.usermanager.domain.confirmationtoken;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface ConfirmationTokenRepository extends Repository<ConfirmationToken, Long> {
    ConfirmationToken save(ConfirmationToken token);

    @Query("SELECT ct FROM ConfirmationToken ct WHERE ct.value = :value")
    Optional<ConfirmationToken> findByValue(String value);

    @Modifying
    @Query("DELETE FROM ConfirmationToken ct WHERE ct.id = :id")
    void deleteById(Long id);

    @Modifying
    @Query("DELETE FROM ConfirmationToken ct WHERE ct.user.id = :userId")
    void deleteByUserId(Long userId);
}