package com.example.Toda.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tourguide")
@PreAuthorize("hasRole('TOURGUIDE')")
public class TourGuideController {

    @GetMapping("/profile")
    public String getProfile() {
        return "Tour Guide Profile";
    }

    @GetMapping("/tours")
    public String getMyTours() {
        return "My tours list";
    }

    @PostMapping("/tours")
    public String createTour() {
        return "Tour created";
    }
}
