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
        boolean response = false;
        List<PasswordHistory> passwordHistoryList =
                passwordHistoryRepository.findLastThreeRegister(userEmployee.getIdUser());
        for(int i = 0; i < passwordHistoryList.size(); i++) {
            if(bCryptPasswordEncoder.matches(password, passwordHistoryList.get(i).getUserEmployee().getPassword())) {
                response = true;
                break;
            }
        }
        return response;
    }
}
