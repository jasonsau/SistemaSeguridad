package com.example.demo.paswword;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.user.UserEmployee;

@Service
public class PasswordHistoryService {

    private final PasswordHistoryRepository  passwordHistoryRepository;

    public PasswordHistoryService(PasswordHistoryRepository passwordHistoryRepository) {
        this.passwordHistoryRepository = passwordHistoryRepository;
    }

    public void savePasswordHistory(PasswordHistory passwordHistory) {
         passwordHistoryRepository.save(passwordHistory);
    }
    
    public ArrayList<PasswordHistory> findByIdUser(long idUser) {
        return passwordHistoryRepository.findByUserId(idUser);
    }
}
