package com.example.Toda.DTO;

import java.math.BigDecimal;

public record HotelRequest(
    String name,
    Integer rating,
    BigDecimal pricePerDay,
    String description,
    String imageUrl,
    Long cityId
) {}
