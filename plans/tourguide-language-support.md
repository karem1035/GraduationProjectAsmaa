# TourGuide Language Support Implementation Plan

## Overview

Add language support for TourGuides with an array of language codes and a filter endpoint to find tour guides by language.

---

## Implementation Plan

### 1. Create TourGuide Entity

**File:** `Toda/src/main/java/com/example/Toda/Entity/TourGuideEntity.java` (NEW)

```java
package com.example.Toda.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tourguide")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourGuideEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String bio;

    @ElementCollection
    @CollectionTable(name = "tourguide_languages", joinColumns = @JoinColumn(name = "tourguide_id"))
    @Column(name = "language_code")
    private List<String> languages;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    private UserEntity user;
}
```

---

### 2. Create TourGuide Repository

**File:** `Toda/src/main/java/com/example/Toda/repo/TourGuideRepo.java` (NEW)

```java
package com.example.Toda.repo;

import com.example.Toda.Entity.TourGuideEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TourGuideRepo extends JpaRepository<TourGuideEntity, Long> {

    Optional<TourGuideEntity> findByUser_Id(Long userId);

    @Query("SELECT tg FROM TourGuideEntity tg JOIN tg.languages l WHERE l = :languageCode")
    List<TourGuideEntity> findByLanguageCode(@Param("languageCode") String languageCode);
}
```

---

### 3. Create TourGuide DTOs

**File:** `Toda/src/main/java/com/example/Toda/DTO/TourGuideRequest.java` (NEW)

```java
package com.example.Toda.DTO;

import java.util.List;

public record TourGuideRequest(
        String name,
        String bio,
        List<String> languages
) {
}
```

**File:** `Toda/src/main/java/com/example/Toda/DTO/TourGuideResponse.java` (NEW)

```java
package com.example.Toda.DTO;

import java.util.List;

public record TourGuideResponse(
        Long id,
        String name,
        String bio,
        List<String> languages
) {
}
```

---

### 4. Create TourGuide Service

**File:** `Toda/src/main/java/com/example/Toda/service/TourGuideService.java` (NEW)

```java
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
```

---

### 5. Update TourGuide Controller

**File:** `Toda/src/main/java/com/example/Toda/controller/TourGuideController.java`

```java
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
```

---

## API Endpoints Summary

### TourGuide-Only Endpoints (Protected)

| Endpoint                 | Method | Description                      |
| ------------------------ | ------ | -------------------------------- |
| `/api/tourguide/profile` | GET    | Get current tour guide's profile |
| `/api/tourguide/profile` | POST   | Create tour guide profile        |
| `/api/tourguide/tours`   | GET    | Get my tours                     |
| `/api/tourguide/tours`   | POST   | Create tour                      |

### Public Endpoints (for filtering tour guides)

| Endpoint                                 | Method | Description                         |
| ---------------------------------------- | ------ | ----------------------------------- |
| `/api/tourguides`                        | GET    | Get all tour guides                 |
| `/api/tourguides/filter?languageCode=en` | GET    | Filter tour guides by language code |

---

## Database Schema

### New Tables

- **`tourguide`** - Tour guide profiles
  - `id`, `name`, `bio`, `user_id`

- **`tourguide_languages`** - Junction table for languages
  - `tourguide_id`, `language_code`

---

## Language Code Examples

Common language codes (ISO 639-1):

- `en` - English
- `ar` - Arabic
- `fr` - French
- `es` - Spanish
- `de` - German
- `it` - Italian
- `zh` - Chinese
- `ja` - Japanese
- `ko` - Korean

---

## Example API Calls

### Create Tour Guide Profile

```json
POST /api/tourguide/profile
Authorization: Bearer <jwt_token>
{
  "name": "Ahmed Mohamed",
  "bio": "Experienced tour guide with 5 years of experience",
  "languages": ["en", "ar", "fr"]
}
```

### Filter Tour Guides by Language

```
GET /api/tourguides/filter?languageCode=ar
```

Response:

```json
{
  "success": true,
  "message": "Tour guides with language: ar",
  "data": [
    {
      "id": 1,
      "name": "Ahmed Mohamed",
      "bio": "Experienced tour guide with 5 years of experience",
      "languages": ["en", "ar", "fr"]
    }
  ],
  "timestamp": "2026-02-08T01:00:00"
}
```

---

## Testing Checklist

- [ ] Tour guide can create profile with languages
- [ ] Tour guide can get their profile
- [ ] Public users can filter tour guides by language code
- [ ] Public users can get all tour guides
- [ ] Language codes are stored correctly in database
- [ ] Multiple language codes can be stored per tour guide
