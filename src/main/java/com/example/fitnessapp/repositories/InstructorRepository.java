package com.example.fitnessapp.repositories;

import com.example.fitnessapp.models.entities.InstructorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends JpaRepository<InstructorEntity, Integer> {
    InstructorEntity findByName(String name);
}
