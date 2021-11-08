package com.example.demo.user;


import com.example.demo.employee.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
public class UserEmployee implements UserDetails {

    //Attributes
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            initialValue = 1,
            sequenceName = "user_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long idUser;

    @OneToOne
    @JoinColumn(
            nullable = false,
            name = "id_user_employee"
    )
    private Employee employee;

    @JoinColumn(
            nullable = false,
            name = "reset_password_token"
    )
    private String resetPasswordToken;

    private String userNameEmployee;
    private String passwordUserEmployee;
    private LocalDateTime passwordExpiredAt;
    private boolean enabled;
    private boolean blocked;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private int attempts;
    private boolean temporaryPassword;
    private boolean isDoubleAuthenticator;
    private String secretKeyGoogleAuthenticator;
    private boolean isDoubleAuthenticationEmail;
    private boolean isDoubleAuthenticationApp;
    private boolean isDoubleAuthenticationSms;

    //Constructs
    public UserEmployee(String userNameEmployee,
                        Employee employee,
                        boolean enabled,
                        boolean blocked,
                        String passwordUserEmployee,
                        boolean temporaryPassword,
                        LocalDateTime passwordExpiredAt,
                        UserRole userRole) {
        this.userNameEmployee = userNameEmployee;
        this.employee = employee;
        this.enabled = enabled;
        this.blocked = blocked;
        this.passwordUserEmployee = passwordUserEmployee;
        this.temporaryPassword = temporaryPassword;
        this.userRole = userRole;
        this.passwordExpiredAt = passwordExpiredAt;
    }
    public UserEmployee() {}

    //Getters and Setters
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.name());
        return Collections.singleton(authority);
    }

    @Override
    public String getPassword() {
        return passwordUserEmployee;
    }

    @Override
    public String getUsername() {
        return userNameEmployee;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !blocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public boolean getTemporaryPassword() {
        return temporaryPassword;
    }
    public boolean getIsDoubleAuthenticator() {
        return isDoubleAuthenticator;
    }

    public void setIsDoubleAuthenticator(boolean isDoubleAuthenticator) {
        this.isDoubleAuthenticator = isDoubleAuthenticator;
    }

    public Long getIdUser() {
        return idUser;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getUserNameEmployee() {
    	return userNameEmployee;
    }
    public void setUserNameEmployee(String userNameEmployee) {
        this.userNameEmployee = userNameEmployee;
    }

    public void setPasswordUserEmployee(String passwordUserEmployee) {
        this.passwordUserEmployee = passwordUserEmployee;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public boolean isTemporaryPassword() {
        return temporaryPassword;
    }

    public void setTemporaryPassword(boolean temporaryPassword) {
        this.temporaryPassword = temporaryPassword;
    }

    public boolean isDoubleAuthenticator() {
        return isDoubleAuthenticator;
    }

    public void setDoubleAuthenticator(boolean doubleAuthenticator) {
        isDoubleAuthenticator = doubleAuthenticator;
    }

    public String getSecretKeyGoogleAuthenticator() {
        return secretKeyGoogleAuthenticator;
    }

    public void setSecretKeyGoogleAuthenticator(String secretKeyGoogleAuthenticator) {
        this.secretKeyGoogleAuthenticator = secretKeyGoogleAuthenticator;
    }

    public LocalDateTime getPasswordExpiredAt() {
        return passwordExpiredAt;
    }

    public void setPasswordExpiredAt(LocalDateTime passwordExpiredAt) {
        this.passwordExpiredAt = passwordExpiredAt;
    }

    public boolean isDoubleAuthenticationEmail() {
        return isDoubleAuthenticationEmail;
    }

    public void setDoubleAuthenticationEmail(boolean doubleAuthenticationEmail) {
        isDoubleAuthenticationEmail = doubleAuthenticationEmail;
    }

    public boolean isDoubleAuthenticationApp() {
        return isDoubleAuthenticationApp;
    }

    public void setDoubleAuthenticationApp(boolean doubleAuthenticationApp) {
        isDoubleAuthenticationApp = doubleAuthenticationApp;
    }

    public boolean isDoubleAuthenticationSms() {
        return isDoubleAuthenticationSms;
    }

    public void setDoubleAuthenticationSms(boolean doubleAuthenticationSms) {
        isDoubleAuthenticationSms = doubleAuthenticationSms;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }
}
