package com.example.fitnessapp.services;

import com.example.fitnessapp.models.dtos.AttributeDescription;

import java.util.List;

public interface AttributeDescriptionService {
    List<AttributeDescription> findByProgramId(Integer id);
    AttributeDescription findById(Integer id);
    List<AttributeDescription> findAll();
}
