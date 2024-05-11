package com.example.fitnessapp.services;

import com.example.fitnessapp.models.entities.UserEntity;
import com.example.fitnessapp.repositories.UserRepository;

public interface TokenService {
    String generateToken();
    public UserEntity generateTokenForUser(UserEntity user);
    }
