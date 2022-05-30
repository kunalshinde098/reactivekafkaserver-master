package com.study.reactivekafkawebsocket;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

@Service
public class KafkaServiceImpl implements KafkaService {


    private Flux<ReceiverRecord<String, String>> testTopicStream;



    KafkaServiceImpl() throws IOException {

        Properties kafkaProperties = PropertiesLoaderUtils.loadAllProperties("application.properties");
        System.err.println(kafkaProperties);
        kafkaProperties.put(ConsumerConfig.CLIENT_ID_CONFIG, "reactive-consumer");
        kafkaProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "sample-group");
        kafkaProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        kafkaProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        kafkaProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        kafkaProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");

        ReceiverOptions<String, String> receiverOptions = ReceiverOptions.create(kafkaProperties);

        testTopicStream = createTopicCache(receiverOptions, "reactive-test-topic");
    }


    private Flux<ReceiverRecord<String, String>> consumer(String consumer) throws IOException {
        Properties kafkaProperties = PropertiesLoaderUtils.loadAllProperties("application.properties");
        System.err.println(kafkaProperties);
        kafkaProperties.put(ConsumerConfig.CLIENT_ID_CONFIG, consumer+"-id");
        kafkaProperties.put(ConsumerConfig.GROUP_ID_CONFIG, consumer+"-grpid");
        kafkaProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        kafkaProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        kafkaProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        ReceiverOptions<String, String> receiverOptions = ReceiverOptions.create(kafkaProperties);

        return createTopicCache(receiverOptions, "reactive-test-topic");
    }



    public Flux<ReceiverRecord<String, String>> getTestTopicFlux() {

        return testTopicStream;
    }

    @Override
    public Flux<ReceiverRecord<String, String>> getTestTopicFluxConsumer(String consumer) {
        try {
            return this.consumer(consumer);
        } catch (IOException e) {
            return Flux.empty();
        }
    }


    private <T, G> Flux<ReceiverRecord<T, G>> createTopicCache(ReceiverOptions<T, G> receiverOptions, String topicName) {
        ReceiverOptions<T, G> options = receiverOptions.subscription(Collections.singleton(topicName));

        return KafkaReceiver.create(options).receive().cache();
    }
}
