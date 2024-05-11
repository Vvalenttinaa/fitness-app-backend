package com.example.fitnessapp.repositories;

import com.example.fitnessapp.models.entities.AttributedescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttributeDescriptionRepository extends JpaRepository<AttributedescriptionEntity, Integer> {

    List<AttributedescriptionEntity> findAllByProgramId(Integer id);
    AttributedescriptionEntity findAttributedescriptionEntityById(Integer id);
    AttributedescriptionEntity findAllByAttributeId(Integer id);
    List<AttributedescriptionEntity> findAllByDescriptionAndAttributeId(String description, Integer attributeId);


}
