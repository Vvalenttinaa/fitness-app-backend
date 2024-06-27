package com.example.fitnessapp.services.impl;

import com.example.fitnessapp.models.dtos.AttributeDescription;
import com.example.fitnessapp.models.entities.AttributedescriptionEntity;
import com.example.fitnessapp.repositories.AttributeDescriptionRepository;
import com.example.fitnessapp.services.AttributeDescriptionService;
import com.example.fitnessapp.services.LoggerService;
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
    private final LoggerService loggerService;

    public AttributeDescriptionServiceImpl(ModelMapper modelMapper, AttributeDescriptionRepository attributeDescriptionRepository, LoggerService loggerService) {
        this.modelMapper = modelMapper;
        this.attributeDescriptionRepository = attributeDescriptionRepository;
        this.loggerService = loggerService;
    }
    @Override
    public List<AttributeDescription> findByProgramId(Integer id) {
        List<AttributedescriptionEntity> attributeEntities = attributeDescriptionRepository.findAllByProgramId(id);
        loggerService.addLog("Get all attribute descriptions for program with id = " + id);
        return attributeEntities.stream()
                .map(attributeEntity -> modelMapper.map(attributeEntity, AttributeDescription.class))
                .collect(Collectors.toList());
    }
    @Override
    public AttributeDescription findById(Integer id) {
        loggerService.addLog("Find attribute description for id = " + id);
        return modelMapper.map(attributeDescriptionRepository.findAttributedescriptionEntityById(id), AttributeDescription.class);
    }

    @Override
    public List<AttributeDescription> findAll(){
        List<AttributedescriptionEntity> attributeEntities = attributeDescriptionRepository.findAll();
        loggerService.addLog("Find all attribute descriptions");
        return attributeEntities.stream()
                .map(attributeEntity -> modelMapper.map(attributeEntity, AttributeDescription.class))
                .collect(Collectors.toList());
    }

    @Override
    public  List<AttributeDescription> findByAttributeId(Integer id){
        loggerService.addLog("Find all attribute descriptions by attribute id = "+ id);
        List<AttributedescriptionEntity> attributeEntities = attributeDescriptionRepository.findDistinctByDescriptionAndAttributeId(id);
        return attributeEntities.stream()
                .map(attributeEntity -> modelMapper.map(attributeEntity, AttributeDescription.class))
                .collect(Collectors.toList());
    }

}
