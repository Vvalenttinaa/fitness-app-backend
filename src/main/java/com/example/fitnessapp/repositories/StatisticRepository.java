package com.example.fitnessapp.repositories;

import com.example.fitnessapp.models.entities.StatisticEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticRepository extends JpaRepository<StatisticEntity, Integer> {
    List<StatisticEntity> findAllByDiaryId(Integer id);
}
