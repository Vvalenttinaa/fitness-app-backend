package com.example.fitnessapp.services.impl;

import com.example.fitnessapp.models.dtos.Category;
import com.example.fitnessapp.models.entities.CategoryEntity;
import com.example.fitnessapp.repositories.CategoryEntityRepository;
import com.example.fitnessapp.services.CategoryService;
import com.example.fitnessapp.services.LoggerService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryEntityRepository categoryEntityRepository;
    private final ModelMapper modelMapper;
    private final LoggerService loggerService;

    public CategoryServiceImpl(CategoryEntityRepository categoryEntityRepository, ModelMapper modelMapper, LoggerService loggerService) {
        this.categoryEntityRepository = categoryEntityRepository;
        this.modelMapper = modelMapper;
        this.loggerService = loggerService;
    }
    @Override
    public List<Category> findAll() {
        loggerService.addLog("Finding all categories");

        List<CategoryEntity> categories = categoryEntityRepository.findAll();
        List<Category> categoriesDto=categories.stream().map(e->modelMapper.map(e,Category.class)).collect(Collectors.toList());
        return categoriesDto;
    }

    @Override
    public Category findByName(String name) {
        loggerService.addLog("Finding category by name " + name);

        return modelMapper.map(categoryEntityRepository.findCategoryEntityByName(name),Category.class);
    }

    @Override
    public Category findById(Integer id) {
        loggerService.addLog("Finding category by id " + id);

        return modelMapper.map(categoryEntityRepository.findCategoryEntityById(id),Category.class);
    }
}
