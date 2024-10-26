package com.online.automobile.config;

import com.online.automobile.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class PersistanceConfig {
    @Bean
    AuditorAware<User>auditorAware(){
        return new CustomAuditorAware();
    }
}
