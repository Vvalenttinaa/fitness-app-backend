package com.example.fitnessapp.repositories;

import com.example.fitnessapp.models.entities.AttributedescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttributeDescriptionRepository extends JpaRepository<AttributedescriptionEntity, Integer> {

    List<AttributedescriptionEntity> findAllByProgramId(Integer id);
    AttributedescriptionEntity findAttributedescriptionEntityById(Integer id);
    @Query("SELECT a FROM AttributedescriptionEntity a WHERE a.description IN (SELECT DISTINCT b.description FROM AttributedescriptionEntity b WHERE b.attributeId = :attributeId)")
    List<AttributedescriptionEntity> findDistinctByDescriptionAndAttributeId(@Param("attributeId") Integer attributeId);

    List<AttributedescriptionEntity> findAllByDescriptionAndAttributeId(String description, Integer attributeId);

}
