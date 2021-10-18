package com.example.demo.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserEmployeeService implements UserDetailsService {

    private final UserEmployeeRepository userEmployeeRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEmployee userEmployee =  userEmployeeRepository.findByEmail(email)
                .orElseThrow( () ->
                    new UsernameNotFoundException(String.format("No se ha encontrado el %s", email))
                );
        return userEmployee;
    }

    public Optional<UserEmployee> findByEmail(String email) {
        return userEmployeeRepository.findByEmail(email);
    }

    public int updateUserAttempts(String email) {
        int response = 0;
        Optional<UserEmployee> userEmployee = userEmployeeRepository.findByEmail(email);
        if(userEmployee.isPresent()) {
            response = userEmployeeRepository.updateUserAttempts((userEmployee.get().getAttempts() + 1),
                    userEmployee.get().getIdUser());
        }
        return response;
    }

    public boolean chekcedAttempts(String email) {
        Optional<UserEmployee> userEmployee = userEmployeeRepository.findByEmail(email);

        if(userEmployee.isPresent()) {
            if(userEmployee.get().getAttempts() >= 2) {
                userEmployeeRepository.updateUserLocked(userEmployee.get().getIdUser());
                return true;
            }
        }
        return false;
    }

    public void restartAttempts(Long idUser) {
        userEmployeeRepository.restartAttempts(idUser);
    }
}
