package com.example.Toda.repo;

import com.example.Toda.Entity.OtpEntity;
import com.example.Toda.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OTPRepo extends JpaRepository<OtpEntity,Integer> {
    Optional<OtpEntity> findByOtpCode( String otp);
    Optional<OtpEntity> findByUser_Id(long userid);

}
