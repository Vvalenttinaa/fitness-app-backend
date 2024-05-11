package com.example.fitnessapp.repositories;

import com.example.fitnessapp.models.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryEntityRepository extends JpaRepository<CategoryEntity, Integer> {
    CategoryEntity findCategoryEntityById(Integer id);
    CategoryEntity findCategoryEntityByName(String name);
}
