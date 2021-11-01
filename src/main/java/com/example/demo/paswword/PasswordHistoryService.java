package com.example.demo.paswword;

import com.example.demo.user.UserEmployee;
import com.example.demo.user.UserEmployeeService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PasswordHistoryService {

    private final PasswordHistoryRepository  passwordHistoryRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public PasswordHistoryService(PasswordHistoryRepository passwordHistoryRepository,
                                  BCryptPasswordEncoder bCryptPasswordEncoder)
                                   {
        this.passwordHistoryRepository = passwordHistoryRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void savePasswordHistory(PasswordHistory passwordHistory) {
         passwordHistoryRepository.save(passwordHistory);
    }

    public boolean verifiedLastestPassword(String password, UserEmployee userEmployee) {
        int contadorMatches = 0;
        List<PasswordHistory> passwordHistoryList =
                passwordHistoryRepository.findLastThreeRegister(userEmployee.getIdUser());
        for (PasswordHistory passwordHistory: passwordHistoryList) {
            System.out.println(passwordHistory.getUserEmployee());
            System.out.println(passwordHistory.getUserEmployeePasswordHistory());
        }
        for(PasswordHistory passwordHistory: passwordHistoryList) {
            if(bCryptPasswordEncoder.matches(password, passwordHistory.getUserEmployeePasswordHistory())) {
                contadorMatches = contadorMatches + 1;
            }
        }
        System.out.println(contadorMatches);
        return contadorMatches > 0;
    }
}
