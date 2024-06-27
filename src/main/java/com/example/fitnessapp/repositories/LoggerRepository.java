package com.example.fitnessapp.repositories;

import com.example.fitnessapp.models.entities.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoggerRepository extends JpaRepository<LogEntity,Integer> {
}

