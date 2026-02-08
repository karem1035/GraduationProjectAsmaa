package com.example.Toda.DTO;

import java.math.BigDecimal;

public record HotelResponse(
    Long id,
    String name,
    Integer rating,
    BigDecimal pricePerDay,
    String description,
    String imageUrl,
    CityResponse city
) {}
