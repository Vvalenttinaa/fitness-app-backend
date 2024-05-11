package com.example.fitnessapp.repositories;

import com.example.fitnessapp.models.entities.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Integer> {
    CityEntity findByName(String city);

    Boolean existsByName(String city);
}
