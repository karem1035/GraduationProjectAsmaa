package com.example.Toda.repo;

import com.example.Toda.Entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<CityEntity, Long> {
    Optional<CityEntity> findByName(String name);
    boolean existsByName(String name);
}
