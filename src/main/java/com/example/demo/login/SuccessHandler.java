package com.example.demo.login;

import com.example.demo.user.UserEmployee;
import com.example.demo.user.UserEmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class SuccessHandler implements AuthenticationSuccessHandler {
    private final UserEmployeeService userEmployeeService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication)
            throws IOException, ServletException {
        UserEmployee userEmployee = (UserEmployee) authentication.getPrincipal();

        if(userEmployee.getAttempts() > 0) {
            userEmployeeService.restartAttempts(userEmployee.getIdUser());
            httpServletResponse.sendRedirect("/home");
        }

    }
}
