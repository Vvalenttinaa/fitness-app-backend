package com.example.fitnessapp.services;

import com.example.fitnessapp.models.dtos.Category;

import java.util.List;

public interface CategoryService {
    List<Category>findAll();
    Category findByName(String name);
    Category findById(Integer id);

}
