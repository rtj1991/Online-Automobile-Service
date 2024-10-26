package com.online.automobile.config;

import com.online.automobile.service.user.AutomobileUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class securityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsService")
    private AutomobileUserDetailService userDetailsService;

    @Autowired
    private LoginHandler successHandler;

    @Autowired
    private CustomLogoutHandler logoutHandler;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/","/resources/**").permitAll()
                .antMatchers("/login/**", "/css/**", "/dashboard", "/assets/**", "/js/**", "/webjars/**", "fonts/**", "/images/**","/resources/**", "/icon/**", "/error/**", "/api/**", "/register", "/createUser","/findService","/createRootUser", "/").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .successHandler(successHandler)
                .and()
                .logout().addLogoutHandler(logoutHandler)
                .logoutSuccessUrl("/login?logout")
                .and()
                .httpBasic()
                .and()
                .sessionManagement()
                .maximumSessions(1)
                .expiredUrl("/login");

    }

}
