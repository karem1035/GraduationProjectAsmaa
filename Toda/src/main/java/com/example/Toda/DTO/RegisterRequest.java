package com.example.Toda.DTO;

import com.example.Toda.Entity.Role;

public record RegisterRequest(
        String username,
        String email,
        String password,
        Role role
) {
    // Constructor with default role
    public RegisterRequest(String username, String email, String password) {
        this(username, email, password, Role.TOURIST);
    }
}
