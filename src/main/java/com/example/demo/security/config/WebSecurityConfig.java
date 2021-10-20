package com.example.demo.security.config;

import com.example.demo.login.FailureHandler;
import com.example.demo.login.SuccessHandler;
import com.example.demo.user.UserEmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
/*    private final SuccessHandler successHandler;
    private final FailureHandler failureHandler;
    private final UserEmployeeService userEmployeeService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login", "/login-check", "/authentication/**", "/verification-code")
                .permitAll().anyRequest().authenticated();
              //  .anyRequest()
             //   .authenticated()
            //    .and()
      //          .formLogin()
       //         .loginPage("/login")
        //        .loginProcessingUrl("/login-check")
         //       .defaultSuccessUrl("/home")
          //      .failureHandler(failureHandler)
           //     .successHandler(successHandler);
    }

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }*/

    /*@Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(userEmployeeService);
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }*/

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
