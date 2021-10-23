package com.example.demo.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserEmployeeService implements UserDetailsService {

    private final UserEmployeeRepository userEmployeeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserEmployeeService(UserEmployeeRepository userEmployeeRepository,
                               BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userEmployeeRepository = userEmployeeRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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

    public int updatePassword(String password, String username) {
        return userEmployeeRepository.updatePassword(bCryptPasswordEncoder.encode(password), username);
    }

    public void restartAttempts(Long idUser) {
        userEmployeeRepository.restartAttempts(idUser);
    }

}
