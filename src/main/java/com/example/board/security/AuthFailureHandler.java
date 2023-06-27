package com.example.board.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

@Configuration
@Slf4j
public class AuthFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {

        String message = null;

        if (request.getParameter("username").length() < 2) {
            message = "Username should be at least 2 characters long.";
        }
        else if (request.getParameter("password").length() < 2) {
            message = "Password should be at least 2 characters long.";
        }
        else if (exception instanceof AuthenticationServiceException) {
            message = "System Error!";
        }
        else if (exception instanceof UsernameNotFoundException) {
            message = "User does not exist!";
        }
        else if (exception instanceof BadCredentialsException) {
            message = "Wrong Credentials.";
        } else {
            message = "Other error";
        }

        response.setContentType("text/html;charset=UTF-8");
        request.setAttribute("message", message);
        request.getRequestDispatcher("/sign-in").forward(request, response);

    }
}
