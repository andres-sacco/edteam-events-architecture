package com.edteam.reservations.messaging.consumer;

import com.edteam.reservations.dto.PaymentDTO;
import com.edteam.reservations.dto.PaymentStatusDTO;
import com.edteam.reservations.enums.APIError;
import com.edteam.reservations.exception.EdteamException;
import com.edteam.reservations.model.Status;
import com.edteam.reservations.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
public class PaymentConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentConsumer.class);

    private final ReservationService service;

    @Autowired
    public PaymentConsumer(ReservationService service) {
        this.service = service;
    }

    @RetryableTopic(backoff = @Backoff(delay = 3000), attempts = "2", kafkaTemplate = "kafkaPaymentTemplate", dltStrategy = DltStrategy.NO_DLT)
    @KafkaListener(topics = "payments", containerFactory = "consumerListenerPaymentConsumerFactory")
    public void listen(PaymentDTO message) {
        LOGGER.info("Received message: {}", message);

        if (message.getStatus().equals(PaymentStatusDTO.ACCEPTED.toString())) {
            service.changeStatus(message.getId(), Status.FINISHED);

        } else if (message.getStatus().equals(PaymentStatusDTO.IN_PROGRESS.toString())) {
            service.changeStatus(message.getId(), Status.IN_PROGRESS);

        } else {
            throw new EdteamException(APIError.BAD_FORMAT);
        }
    }

    @DltHandler
    public void processMessage(PaymentDTO message) {
        LOGGER.info("Error with the message: {}", message);
    }
}