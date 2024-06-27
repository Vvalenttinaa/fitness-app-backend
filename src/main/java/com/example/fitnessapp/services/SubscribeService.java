package com.example.fitnessapp.services;

import com.example.fitnessapp.models.dtos.Subscription;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscribeService {
    Subscription subscribeToCategory(Integer userId, Integer categoryId);
    List<Subscription> getAllByUser(Integer id);
}
