package com.example.Toda.service;

import com.example.Toda.Entity.OtpEntity;
import com.example.Toda.Entity.UserEntity;
import com.example.Toda.exception.UserNotFoundException;
import com.example.Toda.repo.OTPRepo;
import com.example.Toda.repo.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class restPasswordService {
    private final UserRepo userRepo;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public restPasswordService(UserRepo userRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepo;

        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void ResetPassword(String email, String newPassword) {

        UserEntity user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepo.save(user);

    }
}