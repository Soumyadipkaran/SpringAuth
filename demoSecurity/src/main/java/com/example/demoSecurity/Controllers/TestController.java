package com.example.demoSecurity.Controllers;

import com.example.demoSecurity.Service.TestService;
import com.example.demoSecurity.model.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoSecurity.model.AppUser;
import com.example.demoSecurity.repository.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.Authentication;

@RestController
public class TestController {

    @Autowired
    private AppUserRepository repo;

    @Autowired
    private TestService testService;

    @Autowired
    private BCryptPasswordEncoder Bencoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public String signup(@RequestBody AppUser user) {
        user.setPassword(Bencoder.encode(user.getPassword()));
        if (user.getRole() == null) user.setRole("USER");
        repo.save(user);
        return "User created successfully!";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );

        return ResponseEntity.ok("Logged in as: " + auth.getName());
    }




    @GetMapping("/port")
    public String Test() {
        return "Working";
    }



    @GetMapping("/public/test")
    public String publicHello() {
        return "public";
    }

    @GetMapping("/user/home")
    @PreAuthorize("hasRole('USER')")
    public String userHome() {
        return "user home";
    }

    @GetMapping("/manager/home")
    @PreAuthorize("hasRole('MANAGER')")
    public String managerHome() {
        return "manager home";
    }

    @GetMapping("/admin/home")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminHome() {
        return "admin home";
    }

    @GetMapping("/admin-or-manager")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public String adminOrManager() {
        return "admin or manager";
    }

    @GetMapping("/service/admin-task")
    public String adminServiceTask() {
        return testService.adminTask();
    }

    @GetMapping("/service/manager-task")
    public String managerServiceTask() {
        return testService.managerTask();
    }
}
