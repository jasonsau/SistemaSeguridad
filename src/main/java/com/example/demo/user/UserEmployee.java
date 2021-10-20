package com.example.demo.user;


import com.example.demo.employee.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserEmployee implements UserDetails {

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


    private String userNameEmployee;
    private String passwordUserEmployee;
    private boolean enabled;
    private boolean blocked;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private int attempts;
    private boolean temporaryPassword;
    private boolean isDoubleAuthenticator;
    private String secretKeyGoogleAuthenticator;

    public UserEmployee(String userNameEmployee,
                        Employee employee,
                        boolean enabled,
                        boolean blocked,
                        String passwordUserEmployee,
                        boolean temporaryPassword,
                        UserRole userRole) {
        this.userNameEmployee = userNameEmployee;
        this.employee = employee;
        this.enabled = enabled;
        this.blocked = blocked;
        this.passwordUserEmployee = passwordUserEmployee;
        this.temporaryPassword = temporaryPassword;
        this.userRole = userRole;
    }

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
}
