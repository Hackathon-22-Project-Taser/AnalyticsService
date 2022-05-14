package de.unistuttgart.hackathon.taser.AnalyticsService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.LinkedList;

@RestController
public class AnalyticsController {

    /**
     * Offers the Stats for a specific room
     *
     * //TODO: Specify the returned Statistics
     */
    @GetMapping("/analytics/{roomIdentifier}")
    public Collection<String> getStatsForRoom(@PathVariable final String roomIdentifier){
        return new LinkedList<>();
    }

}
