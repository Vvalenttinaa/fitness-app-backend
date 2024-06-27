package com.example.fitnessapp.services;

import org.springframework.stereotype.Service;

@Service
public interface LoggerService {
    void addLog(String log);
}
