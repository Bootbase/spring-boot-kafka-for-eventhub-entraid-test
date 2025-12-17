package com.example.service;

import javax.annotation.PostConstruct;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnExpression("'${app.mode:both}' != 'producer'")
public class KafkaConsumerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);

    @Value("${app.eventhub.topic}")
    private String topic;

    @PostConstruct
    public void init() {
        log.info("KafkaConsumerService initialized - listening on topic [{}] with group [{}]", topic, "$Default");
    }

    @KafkaListener(topics = "${app.eventhub.topic}", groupId = "$Default")
    public void consume(ConsumerRecord<String, String> record) {
        log.info("\n========================================\n" +
                 "CONSUMER: Message received from topic [{}]\n" +
                 "Message: {}\n" +
                 "Partition: {}\n" +
                 "Offset: {}\n" +
                 "========================================",
                record.topic(),
                record.value(),
                record.partition(),
                record.offset());
    }
}
