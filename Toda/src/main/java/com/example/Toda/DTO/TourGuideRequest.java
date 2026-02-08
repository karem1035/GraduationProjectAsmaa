package com.example.Toda.DTO;

import java.util.List;

public record TourGuideRequest(
        String name,
        String bio,
        List<String> languages
) {
}
