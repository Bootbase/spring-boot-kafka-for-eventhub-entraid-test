package com.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String message) {
        log.info("\n========================================\n" +
                 "PRODUCER: Sending message to topic [{}]\n" +
                 "Message: {}\n" +
                 "========================================", topic, message);

        Message<String> kafkaMessage = MessageBuilder
                .withPayload(message)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();

        kafkaTemplate.send(kafkaMessage)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        log.error("\n========================================\n" +
                                  "PRODUCER: Failed to send message!\n" +
                                  "Topic: {}\n" +
                                  "Error: {}\n" +
                                  "========================================", topic, exception.getMessage());
                    } else {
                        log.info("\n========================================\n" +
                                 "PRODUCER: Message sent successfully!\n" +
                                 "Topic: {}\n" +
                                 "Partition: {}\n" +
                                 "Offset: {}\n" +
                                 "========================================",
                                topic,
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    }
                });
    }
}
