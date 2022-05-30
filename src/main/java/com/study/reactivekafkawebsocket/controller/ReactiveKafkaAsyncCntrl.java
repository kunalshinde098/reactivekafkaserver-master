package com.study.reactivekafkawebsocket.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.reactivekafkawebsocket.KafkaService;
import com.study.reactivekafkawebsocket.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/kafka")
public class ReactiveKafkaAsyncCntrl {

    private static final ObjectMapper json = new ObjectMapper();

    @Autowired()
    KafkaService kafkaService;

    @RequestMapping(value = "/get", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> listenKafkaTopicMsg(){
        return kafkaService.getTestTopicFlux()
                .map(record -> {
                    Message message = new Message("[Kafka] Add message", record.value());
                    String response;
                    try {
                        return json.writeValueAsString(message);
                    } catch (JsonProcessingException e) {
                        return "Error while serializing to JSON";
                    }
                });

    }

    @RequestMapping(value = "/{consumer}/get", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> listenKafkaTopicMsg(@PathVariable String consumer){
        return kafkaService.getTestTopicFluxConsumer(consumer)
                .map(record -> {
                    Message message = new Message("[Kafka] Add message", record.value());
                    String response;
                    try {
                        return json.writeValueAsString(message);
                    } catch (JsonProcessingException e) {
                        return "Error while serializing to JSON";
                    }
                });

    }

}
