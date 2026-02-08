package com.example.Toda.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class Admin {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "Admin Dashboard - Welcome!";
    }

    @GetMapping("/users")
    public String getAllUsers() {
        return "List of all users";
    }

    @PostMapping("/users/{id}/role")
    public String updateUserRole(@PathVariable Long id, @RequestParam String newRole) {
        return "User role updated";
    }
}
