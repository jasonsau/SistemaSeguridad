package com.example.demo.user;

import com.example.demo.paswword.PasswordHistory;
import com.example.demo.paswword.PasswordHistoryService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserEmployeeService implements UserDetailsService {

    private final UserEmployeeRepository userEmployeeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PasswordHistoryService passwordHistoryService;
    private final int DATE_EXPIRET_PASSWORD = 30;
    private final String REGEX_PASSWORD = "(?=(.*[0-9]))(?=.*[!@#$%^&*()\\[\\]\\{\\}\\-+=~`\"'<>,./?])(?=.*[a-z])(?=" +
            "(.*[A-Z]))(?=(.*)).{12,}";


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
                    bCryptPasswordEncoder.encode(password)
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

    public void updateUserUnlockedUser(Long idUser) {
        userEmployeeRepository.updateUserUnlocked(idUser);
    }

    public List<UserEmployee> selectUsers() {
        return userEmployeeRepository.findAll();
    }
    
    public List<UserEmployee> getAll(){
    	return userEmployeeRepository.findAll();
    }

    public int getAgeUserEmployee(LocalDate dateNow, LocalDate dateBirth) {
        int age = dateNow.getYear() - dateBirth.getYear();
        if(dateNow.getMonthValue() < dateBirth.getMonthValue()) {
            age = age - 1;
        }
        if(dateNow.getMonthValue() == dateBirth.getMonthValue()) {
            if(dateNow.getDayOfMonth() < dateBirth.getDayOfMonth()){
                age = age -1;
            }
        }
        return age;
    }


    public Authentication getAuthentication(UserEmployee userEmployee){
        return new UsernamePasswordAuthenticationToken(
                userEmployee.getUsername(),
                userEmployee.getPassword(),
                userEmployee.getAuthorities()
        );
    }
    public Authentication getAuthentication(String username,
                                            String password,
                                            Collection<? extends GrantedAuthority> role){
        return new UsernamePasswordAuthenticationToken(
                username,
                password,
                role
        );
    }

    public Collection<? extends GrantedAuthority> addRole(String nameRole) {
        return switch (nameRole) {
            case "CHANGE_PASSWORD" -> Collections.singleton(new SimpleGrantedAuthority(UserRole.CHANGE_PASSWORD.name()));
            case "AUTHENTICATOR" -> Collections.singleton(new SimpleGrantedAuthority(UserRole.AUTHENTICATOR.name()));
            default -> null;
        };
    }

    public void setAuthentication(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public boolean verifiedPasswordTemporary(UserEmployee userEmployee) {
        return userEmployee.getPasswordExpiredAt().isBefore(LocalDateTime.now())
                || userEmployee.getTemporaryPassword();
    }

    public boolean validatePassword(String password) {
        Pattern pattern = Pattern.compile(REGEX_PASSWORD);
        return pattern.matcher(password).find();
    }

    public int enabledUser(Long idUser) {
        return userEmployeeRepository.enabledUser(idUser);
    }

    public void delete(Long id){
        this.userEmployeeRepository.deleteById(id);
    }
}
