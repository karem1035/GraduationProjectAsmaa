package com.example.Toda.DTO;

import java.math.BigDecimal;

public record HotelSearchResponse(
    Long id,
    String name,
    Integer rating,
    BigDecimal pricePerDay,
    BigDecimal totalCost,
    String description,
    String imageUrl,
    CityResponse city
) {}
