package com.example.fitnessapp.repositories;

import com.example.fitnessapp.models.entities.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Integer> {
    List<StatusEntity> findAllByUserId(Integer id);
    StatusEntity findByUserIdAndProgramId(Integer userId, Integer programId);
    Boolean existsByUserIdAndProgramId(Integer userId, Integer programId);
}
