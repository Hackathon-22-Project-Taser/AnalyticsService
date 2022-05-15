package de.unistuttgart.hackathon.taser.AnalyticsService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Timer;

@SpringBootApplication
public class AnalyticsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnalyticsServiceApplication.class, args);

		Timer t = new Timer();
		CronJob cronJob = new CronJob();
		t.scheduleAtFixedRate(cronJob, 0, 10000);//10s = 10000
	}

}
