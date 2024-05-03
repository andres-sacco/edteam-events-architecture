package com.edteam.reservations.messaging.consumer;

import com.edteam.reservations.dto.PaymentDTO;
import com.edteam.reservations.dto.PaymentStatusDTO;
import com.edteam.reservations.enums.APIError;
import com.edteam.reservations.exception.EdteamException;
import com.edteam.reservations.model.Status;
import com.edteam.reservations.service.ReservationService;
import io.github.springwolf.core.asyncapi.annotations.AsyncListener;
import io.github.springwolf.core.asyncapi.annotations.AsyncOperation;
import io.github.springwolf.plugins.kafka.asyncapi.annotations.KafkaAsyncOperationBinding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
public class PaymentConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentConsumer.class);

    private static final String TOPIC = "payments";

    private final ReservationService service;

    @Autowired
    public PaymentConsumer(ReservationService service) {
        this.service = service;
    }

    @AsyncListener(operation = @AsyncOperation(channelName = TOPIC, description = "On this topic you will receive the notifications about the payment"))
    @KafkaAsyncOperationBinding
    @RetryableTopic(backoff = @Backoff(delay = 3000), attempts = "2", kafkaTemplate = "kafkaPaymentTemplate", dltStrategy = DltStrategy.NO_DLT)
    @KafkaListener(topics = TOPIC, containerFactory = "consumerListenerPaymentConsumerFactory")
    public void listen(@Payload PaymentDTO message) {
        LOGGER.info("Received message: {}", message);

        if (message.getStatus().equals(PaymentStatusDTO.ACCEPTED.toString())) {
            service.changeStatus(message.getId(), Status.FINISHED);

        } else if (message.getStatus().equals(PaymentStatusDTO.IN_PROGRESS.toString())) {
            service.changeStatus(message.getId(), Status.IN_PROGRESS);

        } else {
            throw new EdteamException(APIError.BAD_FORMAT);
        }
    }
}