package com.example.fitnessapp.repositories;

import com.example.fitnessapp.models.dtos.Attribute;
import com.example.fitnessapp.models.entities.AttributeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttributeRepository extends JpaRepository<AttributeEntity, Integer> {
    List<AttributeEntity> findAllByCategoryId(Integer id);
    List<AttributeEntity> findAll();
    Optional<AttributeEntity> findByName(String name);
}
