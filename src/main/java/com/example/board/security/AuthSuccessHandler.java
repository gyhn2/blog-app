package com.example.board.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Configuration
@Slf4j
public class AuthSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        if (session!=null) {
            String prevUrl = (String)session.getAttribute("prevUrl");
//            log.info(prevUrl);
            if (prevUrl != null && !prevUrl.endsWith("/sign-up") && !prevUrl.endsWith("/sign-in")) {
                session.removeAttribute("prevUrl");
                response.sendRedirect(prevUrl);;
            } else {
                session.removeAttribute("prevUrl");
                response.sendRedirect("/");
            }
        }
        else {
            response.sendRedirect("/");
        }
    }


}
