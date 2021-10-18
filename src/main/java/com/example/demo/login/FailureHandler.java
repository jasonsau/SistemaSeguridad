package com.example.demo.login;

import com.example.demo.user.UserEmployee;
import com.example.demo.user.UserEmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


@Component
public class FailureHandler implements AuthenticationFailureHandler {
    private String emailUser = "";

    @Autowired
    private UserEmployeeService userEmployeeService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        AuthenticationException e)
            throws IOException, ServletException {

        String username = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");


        if(username.isEmpty() || password.isEmpty()) {
            httpServletResponse.sendRedirect("/login");
        }
        else {
            Optional<UserEmployee> userEmployee = userEmployeeService.findByEmail(username);
            if (userEmployee.isPresent()) {
                if(userEmployee.get().isBlocked())
                {
                    httpServletResponse.sendRedirect("/login");
                }
                if(!userEmployee.get().isEnabled()) {
                    httpServletResponse.sendRedirect("/login");
                }
            }
            String error = e.getMessage();
            if(error.equals(String.format("No se ha encontrado el %s",username))){
                httpServletResponse.sendRedirect("/login?error=1");
            }
            if(error.equals("Bad credentials")) {
                emailUser = httpServletRequest.getParameter("username");
                httpServletResponse.sendRedirect("/login?error=2");
            }
        }
    }

    @Bean(name = "Parameter")
    public String getParameterEmail() {
        return emailUser;
    }


}
