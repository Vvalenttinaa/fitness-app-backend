package com.example.fitnessapp.services;

import com.example.fitnessapp.models.dtos.Subscription;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribeService {
    Subscription subscribeToCategory(Integer userId, Integer categoryId);
}
