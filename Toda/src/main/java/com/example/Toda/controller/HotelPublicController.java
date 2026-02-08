package com.example.Toda.controller;

import com.example.Toda.DTO.*;
import com.example.Toda.service.CityService;
import com.example.Toda.service.HotelService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class HotelPublicController {

    private final CityService cityService;
    private final HotelService hotelService;

    public HotelPublicController(CityService cityService, HotelService hotelService) {
        this.cityService = cityService;
        this.hotelService = hotelService;
    }

    // City endpoints
    @GetMapping("/cities")
    public ApiResponse<List<CityResponse>> getAllCities() {
        List<CityResponse> cities = cityService.getAllCities();
        return ApiResponse.success("All cities retrieved", cities);
    }

    @GetMapping("/cities/{id}")
    public ApiResponse<CityResponse> getCityById(@PathVariable Long id) {
        CityResponse city = cityService.getCityById(id);
        return ApiResponse.success("City retrieved", city);
    }

    // Hotel endpoints
    @GetMapping("/hotels")
    public ApiResponse<List<HotelResponse>> getHotelsByCity(@RequestParam Long cityId) {
        List<HotelResponse> hotels = hotelService.getHotelsByCity(cityId);
        return ApiResponse.success("Hotels retrieved for city", hotels);
    }

    @GetMapping("/hotels/search")
    public ApiResponse<List<HotelSearchResponse>> searchHotels(
            @RequestParam Long cityId,
            @RequestParam Integer numberOfDays,
            @RequestParam BigDecimal budget,
            @RequestParam(required = false) Integer minRating,
            @RequestParam(required = false) String hotelName) {

        HotelSearchCriteria criteria = new HotelSearchCriteria(
                cityId,
                numberOfDays,
                budget,
                minRating != null ? minRating : 1,
                hotelName
        );

        List<HotelSearchResponse> hotels = hotelService.searchHotels(criteria);
        return ApiResponse.success("Hotels found matching your criteria", hotels);
    }
}
