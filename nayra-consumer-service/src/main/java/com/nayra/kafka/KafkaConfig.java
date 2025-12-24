package com.nayra.kafka;

import java.util.List;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;

import com.nayra.utils.Constant;

@Configuration
public class KafkaConfig {

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, List<byte[]>> batchFactory(
			ConsumerFactory<String, List<byte[]>> consumerFactory) {

		ConcurrentKafkaListenerContainerFactory<String, List<byte[]>> factory = new ConcurrentKafkaListenerContainerFactory<>();

		factory.setConsumerFactory(consumerFactory);
		factory.setBatchListener(true); // enable batch
		factory.setConcurrency(Constant.THREAD_POOL_SIZE); // number of threads
		return factory;
	}
	
	@Bean
	public NewTopic rawLogTopic() {
	    return TopicBuilder.name("raw-logs-topics")
	            .partitions(Constant.THREAD_POOL_SIZE) // should match with concurrency
	            .replicas(1)
	            .build();
	}

}
