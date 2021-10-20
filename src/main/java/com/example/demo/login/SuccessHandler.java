package com.example.demo.login;

import com.example.demo.security.twofactor.TwoFactorGoogleAuthenticatorService;
import com.example.demo.user.UserEmployee;
import com.example.demo.user.UserEmployeeService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
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
    private final TwoFactorGoogleAuthenticatorService twoFactor;
    private final UserEmployeeService userEmployeeService;

    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication)
            throws IOException, ServletException {

        String url = "";
        UserEmployee userEmployee = (UserEmployee) authentication.getPrincipal();
        if(userEmployee.getAttempts() > 0) {
            userEmployeeService.restartAttempts(userEmployee.getIdUser());
        }
        url = "/home";
        if(userEmployee.getTemporaryPassword()) {
            url = "/password/changePassword";
        }
        if(userEmployee.getIsDoubleAuthenticator()) {
            url = "/authentication";
        }

        httpServletResponse.sendRedirect(url);
    }
}
