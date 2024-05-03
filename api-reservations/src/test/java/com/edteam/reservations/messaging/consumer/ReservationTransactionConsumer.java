package com.edteam.reservations.messaging.consumer;

import java.util.concurrent.CountDownLatch;

import com.edteam.reservations.dto.ReservationTransactionDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ReservationTransactionConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationTransactionConsumer.class);

    private CountDownLatch latch = new CountDownLatch(1);

    private String payload;

    @KafkaListener(topics = "reservation-transactions", containerFactory = "consumerListenerReservationTransactionConsumerFactory")
    public void receive(ConsumerRecord<String, ReservationTransactionDTO> consumerRecord) {
        LOGGER.info("received payload='{}'", consumerRecord.toString());

        payload = consumerRecord.toString();
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

    public String getPayload() {
        return payload;
    }

}
