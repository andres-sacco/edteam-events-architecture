package com.edteam.reservations.messaging.producer;

import com.edteam.reservations.dto.ReservationTransactionDTO;
import io.github.springwolf.core.asyncapi.annotations.AsyncOperation;
import io.github.springwolf.core.asyncapi.annotations.AsyncPublisher;
import io.github.springwolf.plugins.kafka.asyncapi.annotations.KafkaAsyncOperationBinding;
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

    @AsyncPublisher(
            operation = @AsyncOperation(
                    channelName = TOPIC,
                    description = "More details for the outgoing topic"
            )
    )
    @KafkaAsyncOperationBinding
    public void sendMessage(ReservationTransactionDTO message) {
        kafkaPaymentTemplate.send(TOPIC, message);
    }
}
