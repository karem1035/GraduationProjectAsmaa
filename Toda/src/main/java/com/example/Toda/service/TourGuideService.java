package com.example.Toda.service;

import com.example.Toda.DTO.TourGuideRequest;
import com.example.Toda.DTO.TourGuideResponse;
import com.example.Toda.Entity.TourGuideEntity;
import com.example.Toda.Entity.UserEntity;
import com.example.Toda.Entity.UserPrincipal;
import com.example.Toda.repo.TourGuideRepo;
import com.example.Toda.repo.UserRepo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TourGuideService {

    private final TourGuideRepo tourGuideRepo;
    private final UserRepo userRepo;

    public TourGuideService(TourGuideRepo tourGuideRepo, UserRepo userRepo) {
        this.tourGuideRepo = tourGuideRepo;
        this.userRepo = userRepo;
    }

    public TourGuideResponse createTourGuide(TourGuideRequest request, Long userId) {
        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        TourGuideEntity tourGuide = new TourGuideEntity();
        tourGuide.setName(request.name());
        tourGuide.setBio(request.bio());
        tourGuide.setLanguages(request.languages());
        tourGuide.setUser(user);

        TourGuideEntity saved = tourGuideRepo.save(tourGuide);
        return toResponse(saved);
    }

    public TourGuideResponse getTourGuideByUserId(Long userId) {
        TourGuideEntity tourGuide = tourGuideRepo.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("TourGuide profile not found"));
        return toResponse(tourGuide);
    }

    public List<TourGuideResponse> getTourGuidesByLanguage(String languageCode) {
        List<TourGuideEntity> tourGuides = tourGuideRepo.findByLanguageCode(languageCode);
        return tourGuides.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<TourGuideResponse> getAllTourGuides() {
        List<TourGuideEntity> tourGuides = tourGuideRepo.findAll();
        return tourGuides.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private TourGuideResponse toResponse(TourGuideEntity tourGuide) {
        return new TourGuideResponse(
                tourGuide.getId(),
                tourGuide.getName(),
                tourGuide.getBio(),
                tourGuide.getLanguages()
        );
    }
}
