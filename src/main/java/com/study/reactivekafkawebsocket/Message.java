package com.study.reactivekafkawebsocket;

import lombok.Data;

@Data
public class Message {
    String type;
    String message;
	public Message(String type, String message) {
		super();
		this.type = type;
		this.message = message;
	}
}
