package com.edteam.reservations.messaging.producer;

import com.edteam.reservations.dto.ReservationTransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ReservationTransactionProducer {

    private static final String TOPIC = "reservation-transactions";

    private final KafkaTemplate<String, ReservationTransactionDTO> kafkaPaymentTemplate;

    @Autowired
    public ReservationTransactionProducer(KafkaTemplate<String, ReservationTransactionDTO> kafkaPaymentTemplate) {
        this.kafkaPaymentTemplate = kafkaPaymentTemplate;
    }

    public void sendMessage(ReservationTransactionDTO message) {
        kafkaPaymentTemplate.send(TOPIC, message);
    }
}
