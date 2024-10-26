package com.online.automobile.config;

import com.online.automobile.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;

import javax.servlet.http.HttpSession;

public class CustomAuditorAware implements AuditorAware<User> {

    @Autowired
    private HttpSession session;

    @Override
    public User getCurrentAuditor() {
        return (User) session.getAttribute("user");
    }
}
