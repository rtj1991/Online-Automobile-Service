package com.online.automobile.config;

import com.online.automobile.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@Component
@Transactional
public class CustomLogoutHandler implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        User user = (User) request.getSession().getAttribute("user");

        if (authentication != null && authentication.getDetails() != null) {
            try {
                request.getSession().invalidate();
                response.setStatus(HttpServletResponse.SC_OK);
                response.sendRedirect("/login");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
