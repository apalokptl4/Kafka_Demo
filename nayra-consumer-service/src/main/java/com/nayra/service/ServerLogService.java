package com.nayra.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nayra.model.ServerLog;
import com.nayra.repository.ServerLogRepository;
import com.nayra.utils.Constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ServerLogService {

	@Autowired
	private ServerLogRepository serverLogRepository;

	/* Match with Kafka concurrency */
	private final ExecutorService executor = Executors.newFixedThreadPool(Constant.THREAD_POOL_SIZE);

	public void saveLogData(List<ServerLog> serverLogs) {
		try {
			int batchSize = 2000;

			List<List<ServerLog>> partitions = partitionList(serverLogs, batchSize);

			/* Convert each partition into a CompletableFuture task */
			List<CompletableFuture<Void>> futures = partitions.stream()
					.map(partition -> CompletableFuture.runAsync(() -> {
						try {
							serverLogRepository.saveAll(partition);
						} catch (Exception e) {
							log.error("Insert failed:", e);
						}
					}, executor)).collect(Collectors.toList());

			/* Wait for all tasks to complete */
			CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
			log.info("✅ [Each kafka batch] total log saved : " + serverLogs.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<List<ServerLog>> partitionList(List<ServerLog> list, int size) {
		List<List<ServerLog>> partitions = new ArrayList<>();
		for (int i = 0; i < list.size(); i += size) {
			partitions.add(list.subList(i, Math.min(i + size, list.size())));
		}
		return partitions;
	}

}
