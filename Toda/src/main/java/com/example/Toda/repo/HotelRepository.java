package com.example.Toda.repo;

import com.example.Toda.Entity.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<HotelEntity, Long> {
    List<HotelEntity> findByCityId(Long cityId);
    List<HotelEntity> findByCityIdAndRatingGreaterThanEqual(Long cityId, Integer minRating);
    List<HotelEntity> findByCityIdAndNameContainingIgnoreCase(Long cityId, String name);
    List<HotelEntity> findByCityIdAndRatingGreaterThanEqualAndNameContainingIgnoreCase(
        Long cityId, Integer minRating, String name);
}
