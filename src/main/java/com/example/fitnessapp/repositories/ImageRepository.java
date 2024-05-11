package com.example.fitnessapp.repositories;

import com.example.fitnessapp.models.entities.ImageEntity;
import org.hibernate.Incubating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {
}
