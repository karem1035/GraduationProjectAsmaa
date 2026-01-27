package com.example.Toda.exception;

public class OTPNotFoundException extends RuntimeException  {
    public OTPNotFoundException(String message) {
        super(message);
    }
}
