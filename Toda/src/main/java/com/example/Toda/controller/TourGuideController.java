package com.example.Toda.controller;

import com.example.Toda.DTO.ApiResponse;
import com.example.Toda.DTO.TourGuideRequest;
import com.example.Toda.DTO.TourGuideResponse;
import com.example.Toda.Entity.UserEntity;
import com.example.Toda.service.TourGuideService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tourguide")
@PreAuthorize("hasRole('TOURGUIDE')")
public class TourGuideController {

    private final TourGuideService tourGuideService;

    public TourGuideController(TourGuideService tourGuideService) {
        this.tourGuideService = tourGuideService;
    }

    @GetMapping("/profile")
    public ApiResponse<TourGuideResponse> getProfile(@AuthenticationPrincipal UserEntity user) {
        TourGuideResponse response = tourGuideService.getTourGuideByUserId(user.getId());
        return ApiResponse.success("Tour Guide Profile retrieved", response);
    }

    @PostMapping("/profile")
    public ApiResponse<TourGuideResponse> createProfile(
            @AuthenticationPrincipal UserEntity user,
            @RequestBody TourGuideRequest request) {
        TourGuideResponse response = tourGuideService.createTourGuide(request, user.getId());
        return ApiResponse.success("Tour Guide Profile created", response);
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

@RestController
@RequestMapping("/api/tourguides")
public class TourGuidePublicController {

    private final TourGuideService tourGuideService;

    public TourGuidePublicController(TourGuideService tourGuideService) {
        this.tourGuideService = tourGuideService;
    }

    @GetMapping
    public ApiResponse<List<TourGuideResponse>> getAllTourGuides() {
        List<TourGuideResponse> tourGuides = tourGuideService.getAllTourGuides();
        return ApiResponse.success("All tour guides", tourGuides);
    }

    @GetMapping("/filter")
    public ApiResponse<List<TourGuideResponse>> getTourGuidesByLanguage(
            @RequestParam String languageCode) {
        List<TourGuideResponse> tourGuides = tourGuideService.getTourGuidesByLanguage(languageCode);
        return ApiResponse.success("Tour guides with language: " + languageCode, tourGuides);
    }
}
