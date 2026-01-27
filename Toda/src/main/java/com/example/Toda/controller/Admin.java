package com.example.Toda.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class Admin {
    @GetMapping("/")
    public String helloAdmin(){
        return "Hello World from AdminAccess Level ";
    }
}
