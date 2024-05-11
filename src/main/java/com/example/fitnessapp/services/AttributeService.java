package com.example.fitnessapp.services;

import com.example.fitnessapp.models.dtos.Attribute;

import java.util.List;

public interface AttributeService {
    List<Attribute>findAllByCategoryId(Integer id);
    List<Attribute>findAll();
}
