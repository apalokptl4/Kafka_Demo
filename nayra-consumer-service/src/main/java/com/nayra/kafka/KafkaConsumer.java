package com.nayra.kafka;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nayra.model.ServerLog;
import com.nayra.service.ServerLogService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaConsumer {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private ServerLogService serverLogService;

	@KafkaListener(topics = "raw-logs-topics", groupId = "log-group", containerFactory = "batchFactory")
	public void consumeMessage(List<byte[]> bytesLogList) {
		try {
			if (bytesLogList != null && !bytesLogList.isEmpty()) {
				List<ServerLog> list = new ArrayList<>();
				for (byte[] raw : bytesLogList) {
					String logStr = convertByteIntoString(raw);
					if (logStr != null && !logStr.isBlank()) {
						ServerLog serverLog = convertStringToCustomeObject(logStr);
						if (serverLog != null) {
							list.add(serverLog);
						} else {
							log.info("Invalid data : " + serverLog);
						}
					} else {
						log.info("Invalid data : " + logStr);
					}
				}

				/* save data here */
				serverLogService.saveLogData(list);
			} else {
				log.info("Null data received! ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String convertByteIntoString(byte[] byteLog) {
		try {
			return new String(byteLog, StandardCharsets.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ServerLog convertStringToCustomeObject(String logStr) {
		try {
			return objectMapper.readValue(logStr, ServerLog.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
