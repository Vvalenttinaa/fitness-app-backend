package com.example.fitnessapp.services.impl;

import com.example.fitnessapp.models.dtos.FitnessNews;
import com.example.fitnessapp.services.LoggerService;
import com.example.fitnessapp.services.RSSService;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class RSSServiceImpl implements RSSService {

    @Value("${rss}")
    private String link;

    private final LoggerService loggerService;

    public RSSServiceImpl(LoggerService loggerService) {
        this.loggerService = loggerService;
    }

    @Override
    public List<FitnessNews> getFeeds() {
        loggerService.addLog("Getting RSS feeds");

        List<FitnessNews> news = new ArrayList<>();
        try {
            URL source = new URL(link);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(source));
            for (SyndEntry entry : feed.getEntries()) {
                FitnessNews fn = new FitnessNews();
                fn.setCategory(entry.getCategories().get(0).getName());
                fn.setTitle(entry.getTitle());
                fn.setDescription(entry.getDescription().getValue());
                fn.setLink(entry.getLink());
                news.add(fn);
            }
        }
        catch(Exception e){

        }
        return news;
    }
}
