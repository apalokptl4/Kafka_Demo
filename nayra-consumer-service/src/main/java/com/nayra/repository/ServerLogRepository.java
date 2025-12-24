package com.nayra.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nayra.model.ServerLog;

@Repository
public interface ServerLogRepository extends MongoRepository<ServerLog, String> {

	ServerLog findTopByOrderByTimestampAsc();
	ServerLog findTopByOrderByTimestampDesc();
	
}
