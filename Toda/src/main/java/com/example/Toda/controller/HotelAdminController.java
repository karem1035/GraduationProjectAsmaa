package com.example.Toda.controller;

import com.example.Toda.DTO.*;
import com.example.Toda.service.CityService;
import com.example.Toda.service.HotelService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class HotelAdminController {

    private final CityService cityService;
    private final HotelService hotelService;

    public HotelAdminController(CityService cityService, HotelService hotelService) {
        this.cityService = cityService;
        this.hotelService = hotelService;
    }

    // City Management
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

    @PostMapping("/cities")
    public ApiResponse<CityResponse> createCity(@RequestBody CityRequest request) {
        CityResponse city = cityService.createCity(request);
        return ApiResponse.success("City created successfully", city);
    }

    @PutMapping("/cities/{id}")
    public ApiResponse<CityResponse> updateCity(@PathVariable Long id, @RequestBody CityRequest request) {
        CityResponse city = cityService.updateCity(id, request);
        return ApiResponse.success("City updated successfully", city);
    }

    @DeleteMapping("/cities/{id}")
    public ApiResponse<Void> deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
        return ApiResponse.success("City deleted successfully", null);
    }

    // Hotel Management
    @GetMapping("/hotels")
    public ApiResponse<List<HotelResponse>> getAllHotels() {
        List<HotelResponse> hotels = hotelService.getAllHotels();
        return ApiResponse.success("All hotels retrieved", hotels);
    }

    @GetMapping("/hotels/{id}")
    public ApiResponse<HotelResponse> getHotelById(@PathVariable Long id) {
        HotelResponse hotel = hotelService.getHotelById(id);
        return ApiResponse.success("Hotel retrieved", hotel);
    }

    @GetMapping("/hotels/city/{cityId}")
    public ApiResponse<List<HotelResponse>> getHotelsByCity(@PathVariable Long cityId) {
        List<HotelResponse> hotels = hotelService.getHotelsByCity(cityId);
        return ApiResponse.success("Hotels retrieved for city", hotels);
    }

    @PostMapping("/hotels")
    public ApiResponse<HotelResponse> createHotel(@RequestBody HotelRequest request) {
        HotelResponse hotel = hotelService.createHotel(request);
        return ApiResponse.success("Hotel created successfully", hotel);
    }

    @PutMapping("/hotels/{id}")
    public ApiResponse<HotelResponse> updateHotel(@PathVariable Long id, @RequestBody HotelRequest request) {
        HotelResponse hotel = hotelService.updateHotel(id, request);
        return ApiResponse.success("Hotel updated successfully", hotel);
    }

    @DeleteMapping("/hotels/{id}")
    public ApiResponse<Void> deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return ApiResponse.success("Hotel deleted successfully", null);
    }
}
