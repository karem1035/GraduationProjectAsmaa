package com.example.Toda.service;

import com.example.Toda.DTO.CityRequest;
import com.example.Toda.DTO.CityResponse;
import com.example.Toda.Entity.CityEntity;
import com.example.Toda.repo.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<CityResponse> getAllCities() {
        List<CityEntity> cities = cityRepository.findAll();
        return cities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public CityResponse getCityById(Long id) {
        CityEntity city = cityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("City not found with id: " + id));
        return toResponse(city);
    }

    public CityResponse createCity(CityRequest request) {
        if (cityRepository.existsByName(request.name())) {
            throw new RuntimeException("City with name '" + request.name() + "' already exists");
        }

        CityEntity city = new CityEntity();
        city.setName(request.name());
        city.setDescription(request.description());

        CityEntity saved = cityRepository.save(city);
        return toResponse(saved);
    }

    public CityResponse updateCity(Long id, CityRequest request) {
        CityEntity city = cityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("City not found with id: " + id));

        if (!city.getName().equals(request.name()) && cityRepository.existsByName(request.name())) {
            throw new RuntimeException("City with name '" + request.name() + "' already exists");
        }

        city.setName(request.name());
        city.setDescription(request.description());

        CityEntity saved = cityRepository.save(city);
        return toResponse(saved);
    }

    public void deleteCity(Long id) {
        if (!cityRepository.existsById(id)) {
            throw new RuntimeException("City not found with id: " + id);
        }
        cityRepository.deleteById(id);
    }

    private CityResponse toResponse(CityEntity city) {
        return new CityResponse(
                city.getId(),
                city.getName(),
                city.getDescription()
        );
    }
}
