package com.example.Toda.DTO;

import java.util.List;

public record TourGuideResponse(
        Long id,
        String name,
        String bio,
        List<String> languages
) {
}
