package com.example.Toda.service;

import com.example.Toda.DTO.OTP;
import com.example.Toda.Entity.OtpEntity;
import com.example.Toda.exception.OTPNotFoundException;
import com.example.Toda.repo.OTPRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OTPService {
    private final OTPRepo otpRepo;

    public OTPService(OTPRepo otpRepo) {
        this.otpRepo = otpRepo;
    }


    public void verify(String otp) {
       OtpEntity otp1= otpRepo.findByOtpCode(otp)
               .orElseThrow(() -> new OTPNotFoundException("OTP Not Founded"));

        if (otp1.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP Expired");
        }
        otpRepo.delete(otp1);

    }
}
