package com.example.demoSecurity.Controllers;

import com.example.demoSecurity.Service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/public/test")
    public String Test() {
        return "Working On Port 8081";
    }



    @GetMapping("/public/hello")
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
