package com.example.demo.security.twofactor.token;

import com.example.demo.security.twofactor.app.TwoFactorService;
import com.example.demo.user.UserEmployee;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final TwoFactorService twoFactorService;

    public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository,
                                    TwoFactorService twoFactorService) {
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.twoFactorService = twoFactorService;
    }

    public Optional<ConfirmationToken> findByIdToken(Long id) {
        return confirmationTokenRepository.findById(id);
    }

    public ConfirmationToken createNewToken(UserEmployee userEmployee) {
        String secrectKey = twoFactorService.generateSecretKey();
        String token = twoFactorService.getTopCode(secrectKey);
        return new ConfirmationToken(token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                userEmployee);
    }

    public void insertConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    public Optional<ConfirmationToken> getLastRegister(Long idUser) {
        return confirmationTokenRepository.getLastRegister(idUser);
    }

    public void updateDateConfiramtionToken(Long id, LocalDateTime confirmationAtToken) {
        confirmationTokenRepository.updateDateConfiramtionToken(id, confirmationAtToken);

    }

    public ConfirmationToken createNewTokenUnlocked(UserEmployee userEmployee) {
        return new ConfirmationToken(UUID.randomUUID().toString(),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                userEmployee);
    }

    public Optional<ConfirmationToken> findByToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }
}
