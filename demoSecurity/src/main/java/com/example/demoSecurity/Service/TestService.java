package com.example.demoSecurity.Service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @PreAuthorize("hasRole('ADMIN')")
    public String adminTask() {
        return "admin task";
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public String managerTask() {
        return "manager task";
    }

    @PreAuthorize("hasAuthority('USER')")
    public String readTask() {
        return "read task";
    }
}
