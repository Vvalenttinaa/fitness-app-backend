package com.example.fitnessapp.repositories;

import com.example.fitnessapp.models.entities.DiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository extends JpaRepository<DiaryEntity, Integer> {
}
