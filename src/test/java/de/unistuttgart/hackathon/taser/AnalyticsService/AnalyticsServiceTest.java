package de.unistuttgart.hackathon.taser.AnalyticsService;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnalyticsServiceTest {

    @Test
    public void calculateValuesTest(){
        AnalyticsService analyticsService = new AnalyticsService();
        final Queue<Map<LocalDateTime, Boolean>> queue = new LinkedBlockingDeque<>();
        for (int i = 0; i < 5; i++){
            Map<LocalDateTime, Boolean> map = new HashMap<>();
            map.put(LocalDateTime.now(), true);
            queue.add(map);
        }
        for (int i = 0; i < 5; i++){
            Map<LocalDateTime, Boolean> map = new HashMap<>();
            map.put(LocalDateTime.now(), false);
            queue.add(map);
        }
        List<Float> test = analyticsService.calculateValues(queue);
        assertEquals("[0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.5]", test.toString());
    }
}
