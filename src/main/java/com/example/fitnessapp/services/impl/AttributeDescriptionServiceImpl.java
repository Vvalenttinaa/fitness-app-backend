package com.example.fitnessapp.services.impl;

import com.example.fitnessapp.models.dtos.Attribute;
import com.example.fitnessapp.models.dtos.AttributeDescription;
import com.example.fitnessapp.models.entities.AttributeEntity;
import com.example.fitnessapp.models.entities.AttributedescriptionEntity;
import com.example.fitnessapp.repositories.AttributeDescriptionRepository;
import com.example.fitnessapp.services.AttributeDescriptionService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AttributeDescriptionServiceImpl implements AttributeDescriptionService {
    private final ModelMapper modelMapper;
    private final AttributeDescriptionRepository attributeDescriptionRepository;

    public AttributeDescriptionServiceImpl(ModelMapper modelMapper, AttributeDescriptionRepository attributeDescriptionRepository) {
        this.modelMapper = modelMapper;
        this.attributeDescriptionRepository = attributeDescriptionRepository;
    }
    @Override
    public List<AttributeDescription> findByProgramId(Integer id) {
        List<AttributedescriptionEntity> attributeEntities = attributeDescriptionRepository.findAllByProgramId(id);
        return attributeEntities.stream()
                .map(attributeEntity -> modelMapper.map(attributeEntity, AttributeDescription.class))
                .collect(Collectors.toList());
    }
    @Override
    public AttributeDescription findById(Integer id) {
        return modelMapper.map(attributeDescriptionRepository.findAttributedescriptionEntityById(id), AttributeDescription.class);
    }

    @Override
    public List<AttributeDescription> findAll(){
        List<AttributedescriptionEntity> attributeEntities = attributeDescriptionRepository.findAll();
        return attributeEntities.stream()
                .map(attributeEntity -> modelMapper.map(attributeEntity, AttributeDescription.class))
                .collect(Collectors.toList());
    }

}
