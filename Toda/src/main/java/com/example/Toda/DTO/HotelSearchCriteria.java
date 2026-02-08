package com.example.Toda.DTO;

import java.math.BigDecimal;

public record HotelSearchCriteria(
    Long cityId,
    Integer numberOfDays,
    BigDecimal budget,
    Integer minRating,
    String hotelName
) {}
