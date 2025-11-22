package com.example.demoSecurity.config;

import com.example.demoSecurity.model.AppUser;
import com.example.demoSecurity.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AppUserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return User.builder()
                .username(appUser.getUsername())
                .password(appUser.getPassword())
                .authorities("ROLE_" + appUser.getRole())
                .build();
    }
}
