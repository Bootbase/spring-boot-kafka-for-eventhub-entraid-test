package com.example.runner;

import com.example.service.KafkaProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EventHubRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(EventHubRunner.class);

    @Autowired
    private KafkaProducerService producerService;

    @Value("${app.eventhub.topic}")
    private String topicName;

    @Value("${app.mode:both}")
    private String mode;

    @Override
    public void run(String... args) {
        log.info("Running in '{}' mode", mode);

        if ("producer".equalsIgnoreCase(mode) || "both".equalsIgnoreCase(mode)) {
            String testMessage = "Hello from Spring Boot Kafka - " + System.currentTimeMillis();
            producerService.sendMessage(topicName, testMessage);
        }

        if ("producer".equalsIgnoreCase(mode)) {
            log.info("Producer mode: Message sent, exiting in 3 seconds...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.exit(0);
        }

        if ("consumer".equalsIgnoreCase(mode) || "both".equalsIgnoreCase(mode)) {
            log.info("Consumer mode: Listening for messages... Press Ctrl+C to exit.");
            // Keep the application running to receive messages
            try {
                Thread.currentThread().join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.info("Consumer interrupted, shutting down...");
            }
        }
    }
}
