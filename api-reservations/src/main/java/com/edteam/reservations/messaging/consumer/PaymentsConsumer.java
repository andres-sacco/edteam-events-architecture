package com.edteam.reservations.messaging.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentsConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentsConsumer.class);

    @KafkaListener(topics = "payments", groupId = "reservation")
    public void listen(String message) {
        LOGGER.info("Received message: {}", message);
    }
}