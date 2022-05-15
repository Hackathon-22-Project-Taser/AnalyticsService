package de.unistuttgart.hackathon.taser.AnalyticsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import redis.clients.jedis.JedisPooled;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

public class AnalyticsService {

    private final JedisPooled jedisPool = new JedisPooled("redis", 6379);
    private final WebClient webClient;
    private final Logger logger = LoggerFactory.getLogger(AnalyticsService.class);
    public AnalyticsService(){
        String hostname = "http://queue:8080";
        webClient = WebClient.create(hostname);
    }

    /**
     * Calculates the Statistics for a specific room
     *
     * Implemented as a cronjob
     */
    public void calculateStatistics() {
        final Map<String, Queue<Map<LocalDateTime, Boolean>>> queues = getQueueData();
        for (final Map.Entry<String, Queue<Map<LocalDateTime, Boolean>>> map : queues.entrySet()) {
            final String identifier = map.getKey();
            final Queue<Map<LocalDateTime, Boolean>> queue = map.getValue();
            final List<Float> values = calculateValues(queue);
            jedisPool.set(identifier, values.toString());
        }
    }

    /**
     * Calculates list of
     * @param queue to calculate the values for
     * @return a List of floats
     */
    public List<Float> calculateValues(final Queue<Map<LocalDateTime, Boolean>> queue) {
        final List<Float> values = new ArrayList<>(Collections.nCopies(12, 0f));
        long now = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        for (final Map<LocalDateTime, Boolean> map : queue){
            long voteTime = map.entrySet().iterator().next().getKey().toEpochSecond(ZoneOffset.UTC);
            long diff = now - voteTime;
            if (map.entrySet().iterator().next().getValue() && (diff <= 120 )){
                int index = (int)diff/10;
                values.set(index, values.get(index) + 1);
            }
        }
        for (int i = 0; i < values.size(); i++){
            values.set(i, values.get(i)/values.size());
        }
        Collections.reverse(values);
        return values;
    }

    private Map<String, Queue<Map<LocalDateTime, Boolean>>> getQueueData(){
        try{
            return webClient.get()
                    .uri("/queue/getQueues")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Queue<Map<LocalDateTime, Boolean>>>>() {})
                    .block();
        }catch (WebClientRequestException e){
            logger.error("cant connect to queueService to update user data: " + e);
        }
        return new HashMap<>();
    }
}
