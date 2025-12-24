package com.nayra.scheduler;

import java.time.Duration;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nayra.repository.ServerLogRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LogCountScheduler {

	@Autowired
	private ServerLogRepository serverLogRepository;

	@Scheduled(fixedRate = 60000) // Every 60 seconds
	public void printLogCount() {

		long total = serverLogRepository.count();
		if (total != 0) {

			Date start = serverLogRepository.findTopByOrderByTimestampAsc().getTimestamp();
			Date end = serverLogRepository.findTopByOrderByTimestampDesc().getTimestamp();

			long minutes = Duration.between(start.toInstant(), end.toInstant()).toMinutes();
			if (minutes == 0)
				minutes = 1; // Avoid divide-by-zero

			double avgPerMinute = (double) total / minutes;
			log.info("Average logs per minute : " + avgPerMinute);
		}
	}

}
