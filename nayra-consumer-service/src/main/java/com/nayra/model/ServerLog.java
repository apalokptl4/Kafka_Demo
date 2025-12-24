package com.nayra.model;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "server_logs")
public class ServerLog {
	
	@Id
	private String id = UUID.randomUUID().toString();

	private String service;

	private String level;

	private String message;

    private Date timestamp;

}
