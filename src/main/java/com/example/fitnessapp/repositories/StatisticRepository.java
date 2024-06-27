package com.example.fitnessapp.repositories;

import com.example.fitnessapp.models.entities.StatisticEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface StatisticRepository extends JpaRepository<StatisticEntity, Integer> {
    List<StatisticEntity> findAllByDiaryId(Integer id);
    Optional<StatisticEntity> findByDateAndDiaryId(Date date, Integer diaryId);

}
