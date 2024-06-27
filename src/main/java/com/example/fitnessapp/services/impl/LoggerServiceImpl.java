package com.example.fitnessapp.services.impl;

import com.example.fitnessapp.models.entities.LogEntity;
import com.example.fitnessapp.repositories.LoggerRepository;
import com.example.fitnessapp.services.LoggerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class LoggerServiceImpl implements LoggerService {

    private final LoggerRepository loggerRepository;

    @Override
    public void addLog(String description)
    {
        LogEntity loggerEntity= new LogEntity();
        loggerEntity.setDate(new Date());
        loggerEntity.setLog(description);
        loggerRepository.saveAndFlush(loggerEntity);
    }
}