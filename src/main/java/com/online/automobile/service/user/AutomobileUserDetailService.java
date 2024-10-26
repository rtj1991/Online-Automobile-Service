package com.online.automobile.service.user;

import com.online.automobile.model.Role;
import com.online.automobile.model.User;
import com.online.automobile.repository.UserRepository;
import com.online.automobile.util.UtilManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
@Transactional
public class AutomobileUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUserName(username);

        if (user == null) {
            throw new BadCredentialsException("Invalid Username or Password");
        }
        if (user.isEnabled()) {
            org.springframework.security.core.userdetails.User loginUser = new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getPassword(),
                    builAuthorities(user.getRoles())
            );
            return loginUser;
        }
        throw new InternalAuthenticationServiceException("Access to your account is temporarily disabled.\n Please contact your administrator");
    }

    private List<GrantedAuthority> builAuthorities(List<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return authorities;
    }
}
