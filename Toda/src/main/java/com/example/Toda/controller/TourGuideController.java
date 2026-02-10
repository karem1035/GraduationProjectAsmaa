package com.example.Toda.controller;

import com.example.Toda.DTO.ApiResponse;
import com.example.Toda.DTO.TourGuideRequest;
import com.example.Toda.DTO.TourGuideResponse;
import com.example.Toda.service.TourGuideService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tourguide")
public class TourGuideController {

    private final TourGuideService tourGuideService;

    public TourGuideController(TourGuideService tourGuideService) {
        this.tourGuideService = tourGuideService;
    }

    @PostMapping("/profile")
    public ResponseEntity<ApiResponse<TourGuideResponse>> createProfile(@Valid @RequestBody TourGuideRequest request) {
        TourGuideResponse response = tourGuideService.createProfile(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Tour guide profile created successfully"));
    }

    @PatchMapping("/profile/{id}")
    public ResponseEntity<ApiResponse<TourGuideResponse>> updateProfile(
            @PathVariable Long id,
            @RequestBody TourGuideRequest request) {
        TourGuideResponse response = tourGuideService.updateProfile(id, request);
        return ResponseEntity
                .ok()
                .body(ApiResponse.success(response, "Tour guide profile updated successfully"));
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<ApiResponse<TourGuideResponse>> getProfile(@PathVariable Long id) {
        TourGuideResponse response = tourGuideService.getProfileById(id);
        return ResponseEntity
                .ok()
                .body(ApiResponse.success(response, "Tour guide profile retrieved successfully"));
    }
}
