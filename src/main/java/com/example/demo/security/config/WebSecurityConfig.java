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
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login", "/login-check" , "/register", "/register-check",

                        "/unlocked-user/**","/changeAuthenticationForm","/changePasswordUser","/userAccount","/userStatistics","/changePassword/check-password",

                        "/unlocked-user/**", "/enabled-user")

                .permitAll()
                .antMatchers("/change-password", "/change-password-check").hasAuthority(UserRole.CHANGE_PASSWORD.name())
                .antMatchers("/authentication/**", "/api/getMethodsAuthentication", "/options", "/verification-code")
                .hasAuthority(UserRole.AUTHENTICATOR.name())
                .antMatchers("/barCode", "/home", "/users",
                        "/add-2fac", "/api/getMethods2Fac", "/create-secret-key", "api/verification-code-app",
                        "/api/verified-equals-password", "/api/send-email-token", "api/verified-code-email",
                        "api/disabled-method/**")
                .hasAuthority(UserRole.ADMIN.name())
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
        web.ignoring().antMatchers("/", "/css/**", "/js/**", "/icons/**", "/img/**");
    }
}
