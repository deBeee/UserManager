package com.usermanager.domain.confirmationtoken;

import com.usermanager.infrastructure.confirmationtoken.error.exception.ConfirmationTokenNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public ConfirmationToken findByValue(String value) {
        return this.confirmationTokenRepository.findByValue(value)
                .orElseThrow(() -> new ConfirmationTokenNotFoundException("Token %s not found".formatted(value), value));
    }

    @Override
    public ConfirmationToken save(ConfirmationToken ct) {
        return this.confirmationTokenRepository.save(ct);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        this.confirmationTokenRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        this.confirmationTokenRepository.deleteByUserId(userId);
    }

}
