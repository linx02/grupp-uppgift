package com.caw24g.grupp_uppgift.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Auth endpoints - publika
                        .requestMatchers("/api/auth/**").permitAll()

                        // Posts läsning - publik
                        .requestMatchers("GET", "/posts").permitAll()
                        .requestMatchers("GET", "/posts/*").permitAll()

                        // Posts skrivning - kräver auth
                        .requestMatchers("POST", "/posts").authenticated()
                        .requestMatchers("PUT", "/posts/*").authenticated()
                        .requestMatchers("DELETE", "/posts/*").authenticated()

                        // Allt annat kräver auth
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}


