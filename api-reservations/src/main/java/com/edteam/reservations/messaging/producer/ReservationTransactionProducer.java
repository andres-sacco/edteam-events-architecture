package com.edteam.reservations.messaging.producer;

import com.edteam.reservations.dto.ReservationTransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ReservationTransactionProducer {

    private static final String TOPIC = "reservation-transactions";

    private final KafkaTemplate<String, ReservationTransactionDTO> kafkaTemplate;

    @Autowired
    public ReservationTransactionProducer(KafkaTemplate<String, ReservationTransactionDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(ReservationTransactionDTO message) {
        kafkaTemplate.send(TOPIC, message);
    }
}
