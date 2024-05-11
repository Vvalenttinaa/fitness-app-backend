package com.example.fitnessapp.services;

import com.example.fitnessapp.models.dtos.FitnessNews;

import java.util.List;

public interface RSSService {
    List<FitnessNews> getFeeds();
}