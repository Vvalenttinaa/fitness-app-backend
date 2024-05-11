package com.example.fitnessapp.services;

import com.example.fitnessapp.models.dtos.Statistic;
import com.example.fitnessapp.models.requests.StatisticRequest;

import java.util.List;

public interface StatisticService {
    Statistic insertStatistic(StatisticRequest statisticRequest, Integer userId);
    List<Statistic> readStatistic(Integer id);
}
