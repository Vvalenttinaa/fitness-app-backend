package com.example.fitnessapp.repositories;

import com.example.fitnessapp.models.entities.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Integer> {
    LocationEntity findByName(String name);
    boolean existsByName(String name);
}
