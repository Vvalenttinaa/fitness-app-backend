package com.example.fitnessapp.repositories;

import com.example.fitnessapp.models.entities.ProgramEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProgramRepository extends JpaRepository<ProgramEntity, Integer>{
    Page<ProgramEntity> findAll(Pageable pageable);
    List<ProgramEntity> findAllByCategoryId(int id);
    Optional<ProgramEntity> findById(Integer id);
    List<ProgramEntity> findByCategoryIdAndCreatedAtBetween(Integer id, Date date1, Date date2);
}
