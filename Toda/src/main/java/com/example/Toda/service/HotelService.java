package com.example.Toda.service;

import com.example.Toda.DTO.*;
import com.example.Toda.Entity.CityEntity;
import com.example.Toda.Entity.HotelEntity;
import com.example.Toda.repo.CityRepository;
import com.example.Toda.repo.HotelRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;
    private final CityRepository cityRepository;

    public HotelService(HotelRepository hotelRepository, CityRepository cityRepository) {
        this.hotelRepository = hotelRepository;
        this.cityRepository = cityRepository;
    }

    public List<HotelResponse> getAllHotels() {
        List<HotelEntity> hotels = hotelRepository.findAll();
        return hotels.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public HotelResponse getHotelById(Long id) {
        HotelEntity hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));
        return toResponse(hotel);
    }

    public List<HotelResponse> getHotelsByCity(Long cityId) {
        List<HotelEntity> hotels = hotelRepository.findByCityId(cityId);
        return hotels.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public HotelResponse createHotel(HotelRequest request) {
        CityEntity city = cityRepository.findById(request.cityId())
                .orElseThrow(() -> new RuntimeException("City not found with id: " + request.cityId()));

        HotelEntity hotel = new HotelEntity();
        hotel.setName(request.name());
        hotel.setRating(request.rating());
        hotel.setPricePerDay(request.pricePerDay());
        hotel.setDescription(request.description());
        hotel.setImageUrl(request.imageUrl());
        hotel.setCity(city);

        HotelEntity saved = hotelRepository.save(hotel);
        return toResponse(saved);
    }

    public HotelResponse updateHotel(Long id, HotelRequest request) {
        HotelEntity hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));

        CityEntity city = cityRepository.findById(request.cityId())
                .orElseThrow(() -> new RuntimeException("City not found with id: " + request.cityId()));

        hotel.setName(request.name());
        hotel.setRating(request.rating());
        hotel.setPricePerDay(request.pricePerDay());
        hotel.setDescription(request.description());
        hotel.setImageUrl(request.imageUrl());
        hotel.setCity(city);

        HotelEntity saved = hotelRepository.save(hotel);
        return toResponse(saved);
    }

    public void deleteHotel(Long id) {
        if (!hotelRepository.existsById(id)) {
            throw new RuntimeException("Hotel not found with id: " + id);
        }
        hotelRepository.deleteById(id);
    }

    public List<HotelSearchResponse> searchHotels(HotelSearchCriteria criteria) {
        List<HotelEntity> hotels;

        String hotelName = criteria.hotelName();
        boolean hasHotelNameFilter = hotelName != null && !hotelName.isBlank();

        // Apply filters based on provided criteria
        if (criteria.minRating() != null && hasHotelNameFilter) {
            hotels = hotelRepository.findByCityIdAndRatingGreaterThanEqualAndNameContainingIgnoreCase(
                    criteria.cityId(),
                    criteria.minRating(),
                    hotelName
            );
        } else if (criteria.minRating() != null) {
            hotels = hotelRepository.findByCityIdAndRatingGreaterThanEqual(
                    criteria.cityId(),
                    criteria.minRating()
            );
        } else if (hasHotelNameFilter) {
            hotels = hotelRepository.findByCityIdAndNameContainingIgnoreCase(
                    criteria.cityId(),
                    hotelName
            );
        } else {
            hotels = hotelRepository.findByCityId(criteria.cityId());
        }

        // Filter by budget and calculate total cost
        BigDecimal maxPricePerDay = criteria.budget().divide(
                BigDecimal.valueOf(criteria.numberOfDays()),
                2,
                RoundingMode.HALF_UP
        );

        return hotels.stream()
                .filter(hotel -> hotel.getPricePerDay().compareTo(maxPricePerDay) <= 0)
                .map(hotel -> {
                    BigDecimal totalCost = hotel.getPricePerDay().multiply(BigDecimal.valueOf(criteria.numberOfDays()));
                    return toSearchResponse(hotel, totalCost);
                })
                .collect(Collectors.toList());
    }

    private HotelResponse toResponse(HotelEntity hotel) {
        return new HotelResponse(
                hotel.getId(),
                hotel.getName(),
                hotel.getRating(),
                hotel.getPricePerDay(),
                hotel.getDescription(),
                hotel.getImageUrl(),
                new CityResponse(
                        hotel.getCity().getId(),
                        hotel.getCity().getName(),
                        hotel.getCity().getDescription()
                )
        );
    }

    private HotelSearchResponse toSearchResponse(HotelEntity hotel, BigDecimal totalCost) {
        return new HotelSearchResponse(
                hotel.getId(),
                hotel.getName(),
                hotel.getRating(),
                hotel.getPricePerDay(),
                totalCost,
                hotel.getDescription(),
                hotel.getImageUrl(),
                new CityResponse(
                        hotel.getCity().getId(),
                        hotel.getCity().getName(),
                        hotel.getCity().getDescription()
                )
        );
    }
}
