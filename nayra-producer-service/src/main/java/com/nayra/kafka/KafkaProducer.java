package com.nayra.kafka;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nayra.response.ServerLogResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaProducer {

	private final KafkaTemplate<String, byte[]> kafkaTemplate;
	private final ObjectMapper objectMapper;
	private static final String REQUEST_TOPIC = "raw-logs-topics";

	public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate, ObjectMapper objectMapper) {
		super();
		this.kafkaTemplate = kafkaTemplate;
		this.objectMapper = objectMapper;
	}

	String[] services = {"auth", "billing", "api", "frontend"};
    String[] levels = {"INFO", "WARN", "ERROR", "DEBUG"};

	@Scheduled(fixedRate = 10000)
	public void generateLogs() throws JsonProcessingException {
		for (int i = 0; i < 1000000; i++) {
			
			//
			ServerLogResponse log = new ServerLogResponse();
			log.setId(UUID.randomUUID().toString());
			log.setService(services[new Random().nextInt(services.length)]);
            log.setLevel(levels[new Random().nextInt(levels.length)]);
            log.setMessage("Msg-" + UUID.randomUUID());
            log.setTimestamp(new Date());
			//
			
//			ServerLogRequest kafkaMessage = new ServerLogRequest(String.valueOf(i), "hello", Instant.now().toString());
//			String jsonRequest = objectMapper.writeValueAsString(kafkaMessage);
            
            String jsonRequest = objectMapper.writeValueAsString(log);

			byte[] rawLog = jsonRequest.getBytes(StandardCharsets.UTF_8);
			kafkaTemplate.send(REQUEST_TOPIC, rawLog);
		}
		log.info("1000000 : data sent to the consumer ");
	}

}
