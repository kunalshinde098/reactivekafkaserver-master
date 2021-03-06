package com.study.reactivekafkawebsocket;

import reactor.core.publisher.Flux;
import reactor.kafka.receiver.ReceiverRecord;

public interface KafkaService {

    public Flux<ReceiverRecord<String, String>> getTestTopicFlux();
    public Flux<ReceiverRecord<String, String>> getTestTopicFluxConsumer(String consumer);

}
