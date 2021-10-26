package com.example.demo.security.config;

import com.example.demo.user.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login", "/login-check", "/authentication/**", "/verification-code","/register","/register-check","/register")
                .permitAll()
                .antMatchers("/change-password", "/change-password-check").hasAuthority(UserRole.CHANGE_PASSWORD.name())
                .antMatchers("/authentication/**").hasAuthority(UserRole.AUTHENTICATOR.name())
                .antMatchers("/barCode", "/home").hasAuthority(UserRole.ADMIN.name())
                .anyRequest()
                .authenticated();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return authentication -> {
            throw  new AuthenticationServiceException("No se puede autentificar" + authentication);
        };
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/", "/css/**", "/js/**", "/icons/**");
    }
}
