package com.usermanager.domain.confirmationtoken;

import jakarta.transaction.Transactional;


public interface ConfirmationTokenService {

    ConfirmationToken findByValue(String value);

    ConfirmationToken save(ConfirmationToken ct);

    @Transactional
    void deleteById(Long id);

    @Transactional
    void deleteByUserId(Long userId);
}
