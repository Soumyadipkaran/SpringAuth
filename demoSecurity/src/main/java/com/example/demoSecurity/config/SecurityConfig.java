package com.example.demoSecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService uds;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> auth
                // explicitly allow GET method for /port
                .requestMatchers("/port").permitAll()
                .requestMatchers("/public/**").permitAll()
                .requestMatchers("/signup").permitAll()
                .anyRequest().authenticated()
        );

        // Attach userDetailsService with password encoder
        http.userDetailsService(uds);
        //passwordEncoder.matches(rawPassword, hashedPassword)


        // Enable HTTP Basic authentication
        http.httpBasic(httpSecurityHttpBasicConfigurer -> {});

        System.out.println("SecurityFilterChain applied!");

        return http.build();
    }
}
