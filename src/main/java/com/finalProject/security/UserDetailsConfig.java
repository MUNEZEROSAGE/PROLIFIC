package com.finalProject.security;


import com.finalProject.repository.CitizenRepository;
import com.finalProject.serviceImpl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserDetailsConfig {

    @Bean
    public UserDetailsService userDetailsService(CitizenRepository citizenRepository,
                                                 PasswordEncoder passwordEncoder) {
        return new UserDetailsServiceImpl(citizenRepository, passwordEncoder);
    }
}
