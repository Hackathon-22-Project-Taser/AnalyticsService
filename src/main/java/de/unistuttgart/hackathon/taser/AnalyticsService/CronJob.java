package de.unistuttgart.hackathon.taser.AnalyticsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

public class CronJob extends TimerTask {

    private final Logger logger = LoggerFactory.getLogger(CronJob.class);

    /**
     * This method reloades the queues every x seconds.
     */
    @Override
    public void run() {
        AnalyticsService service = new AnalyticsService();
        logger.info("cronjob - refresh queues");
        service.calculateStatistics();
    }
}
