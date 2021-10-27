package com.example.demo.user;

import com.example.demo.paswword.PasswordHistory;
import com.example.demo.paswword.PasswordHistoryService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserEmployeeService implements UserDetailsService {

    private final UserEmployeeRepository userEmployeeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PasswordHistoryService passwordHistoryService;
    private final int DATE_EXPIRET_PASSWORD = 30;

    public UserEmployeeService(UserEmployeeRepository userEmployeeRepository,
                               BCryptPasswordEncoder bCryptPasswordEncoder,
                               PasswordHistoryService passwordHistoryService) {
        this.userEmployeeRepository = userEmployeeRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.passwordHistoryService = passwordHistoryService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userEmployeeRepository.findByUsername(username)
                .orElseThrow( () ->
                    new UsernameNotFoundException(String.format("No se ha encontrado el %s", username))
                );
    }

    public Optional<UserEmployee> findByEmail(String email) {
        return userEmployeeRepository.findByEmail(email);
    }
    public Optional<UserEmployee> findByIdUser(Long idUser) {
        return userEmployeeRepository.findById(idUser);
    }

    public Optional<UserEmployee> findByUsername(String username) {
        return userEmployeeRepository.findByUsername(username);
    }

    public int updateUserAttempts(String username) {
        int response = 0;
        Optional<UserEmployee> userEmployee = userEmployeeRepository.findByUsername(username);
        if(userEmployee.isPresent()) {
            response = userEmployeeRepository.updateUserAttempts((userEmployee.get().getAttempts() + 1),
                    userEmployee.get().getIdUser());
        }
        return response;
    }

    public boolean chekcedAttempts(String username) {
        Optional<UserEmployee> userEmployee = userEmployeeRepository.findByUsername(username);

        if(userEmployee.isPresent()) {
            if(userEmployee.get().getAttempts() >= 2) {
                userEmployeeRepository.updateUserLocked(userEmployee.get().getIdUser());
                return true;
            }
        }
        return false;
    }

    public int updatePassword(String password, String username) {
        Optional<UserEmployee> userEmployee = findByUsername(username);
        if(userEmployee.isPresent()) {
            passwordHistoryService.savePasswordHistory(new PasswordHistory(
                    LocalDateTime.now(),
                    LocalDateTime.now().plusDays(DATE_EXPIRET_PASSWORD),
                    userEmployee.get(),
                    bCryptPasswordEncoder.encode(username)
            ));
        } else {
            throw new IllegalStateException("Usuario no se ha encontrado");
        }

        return userEmployeeRepository.updatePassword(bCryptPasswordEncoder.encode(password), username,
                LocalDateTime.now().plusDays(DATE_EXPIRET_PASSWORD));
    }

    public void restartAttempts(Long idUser) {
        userEmployeeRepository.restartAttempts(idUser);
    }

    public void saveUserEmployee(UserEmployee userEmployee) {
    	userEmployeeRepository.save(userEmployee);
    }

    public List<UserEmployee> selectUsers() {
        return userEmployeeRepository.findAll();
    }

}
