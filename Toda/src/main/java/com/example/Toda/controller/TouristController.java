package com.example.Toda.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tourist")
@PreAuthorize("hasRole('TOURIST')")
public class TouristController {

    @GetMapping("/profile")
    public String getProfile() {
        return "Tourist Profile";
    }

    @GetMapping("/bookings")
    public String getMyBookings() {
        return "My bookings list";
    }

    @PostMapping("/bookings")
    public String bookTour() {
        return "Tour booked";
    }
}
