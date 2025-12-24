package com.nayra.response;

import java.time.Instant;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServerLogResponse {
	
	private String id;
	
	private String service;

	private String level;

	private String message;

    private Date timestamp;
    
	
//	private String requestId;
//	
//    private String message;
//    
//    private String timestamp;
//
//	public KafkaMessage() {
//		super();
//	}
//
//	public KafkaMessage(String requestId, String message,String timestamp) {
//		super();
//		this.requestId = requestId;
//		this.message = message;
//		this.timestamp=timestamp;
//	}
//
//	public String getRequestId() {
//		return requestId;
//	}
//
//	public void setRequestId(String requestId) {
//		this.requestId = requestId;
//	}
//
//	public String getMessage() {
//		return message;
//	}
//
//	public void setMessage(String message) {
//		this.message = message;
//	}
//
//	public String getTimestamp() {
//		return timestamp;
//	}
//
//	public void setTimestamp(String timestamp) {
//		this.timestamp = timestamp;
//	}
//
//	@Override
//	public String toString() {
//		return "KafkaMessage [requestId=" + requestId + ", message=" + message + ", timestamp=" + timestamp + "]";
//	}
//    

}
