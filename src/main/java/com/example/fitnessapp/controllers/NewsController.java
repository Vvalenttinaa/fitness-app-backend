package com.example.fitnessapp.controllers;

import com.example.fitnessapp.models.dtos.FitnessNews;
import com.example.fitnessapp.services.RSSService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final RSSService rssService;

    public NewsController(RSSService rssService) {
        this.rssService = rssService;
    }

    @GetMapping
    public List<FitnessNews> getNews(){
        return this.rssService.getFeeds();
    }
}