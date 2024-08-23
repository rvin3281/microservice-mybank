package com.eazybyte.loanservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurity(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((requests) -> requests
                .anyRequest().permitAll()
        );

        http.csrf(csrf -> csrf.disable());
        // Disable CSRF protection if you don't need it (e.g., for APIs)

        return http.build();
    }

}
