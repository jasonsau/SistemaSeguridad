package com.example.demo.paswword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordHistoryService {
    @Autowired
    PasswordHistoryRepository passwordHistoryRepository;

    public void savePasswordHistory(PasswordHistory passwordHistory) {
         passwordHistoryRepository.save(passwordHistory);
    }
}
