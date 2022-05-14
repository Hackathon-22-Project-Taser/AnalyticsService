package de.unistuttgart.hackathon.taser.AnalyticsService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class AnalyticsController {

    private AnalyticsService analyticsService;

    @PostConstruct
    public void init(){
        analyticsService = new AnalyticsService();
    }

    @PostMapping("/analytics")
    public void getStatsForRoom(){
        analyticsService.calculateStatistics();
    }
}
