package com.example.fitnessapp.services.impl;

import com.example.fitnessapp.models.dtos.Attribute;
import com.example.fitnessapp.models.entities.AttributeEntity;
import com.example.fitnessapp.repositories.AttributeRepository;
import com.example.fitnessapp.services.AttributeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttributeServiceImpl implements AttributeService {

    private final AttributeRepository attributeRepository;
    private final ModelMapper modelMapper;

    public AttributeServiceImpl(AttributeRepository attributeRepository, ModelMapper modelMapper) {
        this.attributeRepository = attributeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Attribute> findAllByCategoryId(Integer id) {
        List<AttributeEntity> attributeEntities = attributeRepository.findAllByCategoryId(id);
        return attributeEntities.stream()
                .map(attributeEntity -> modelMapper.map(attributeEntity, Attribute.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Attribute> findAll() {
        List<AttributeEntity> attributeEntities = attributeRepository.findAll();
        return attributeEntities.stream()
                .map(attributeEntity -> modelMapper.map(attributeEntity, Attribute.class))
                .collect(Collectors.toList());
    }
}
