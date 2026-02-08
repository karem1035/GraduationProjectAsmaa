package com.example.Toda.repo;

import com.example.Toda.Entity.TourGuideEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TourGuideRepo extends JpaRepository<TourGuideEntity, Long> {

    Optional<TourGuideEntity> findByUser_Id(Long userId);

    @Query("SELECT tg FROM TourGuideEntity tg JOIN tg.languages l WHERE l = :languageCode")
    List<TourGuideEntity> findByLanguageCode(@Param("languageCode") String languageCode);
}
