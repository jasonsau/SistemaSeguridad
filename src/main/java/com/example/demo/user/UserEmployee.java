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
    private String password;
    private boolean enabled;
    private boolean blocked;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public UserEmployee(String userNameEmployee,
                        Employee employee,
                        boolean enabled,
                        boolean blocked,
                        String password,
                        UserRole userRole) {
        this.userNameEmployee = userNameEmployee;
        this.employee = employee;
        this.enabled = enabled;
        this.blocked = blocked;
        this.password = password;
        this.userRole = userRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.name());
        return Collections.singleton(authority);
    }

    @Override
    public String getPassword() {
        return password;
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
}
