package com.edteam.reservations.messaging.consumer;

import com.edteam.reservations.dto.PaymentDTO;
import com.edteam.reservations.dto.PaymentStatusDTO;
import com.edteam.reservations.model.Status;
import com.edteam.reservations.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentConsumer.class);

    private ReservationService service;

    @Autowired
    public PaymentConsumer(ReservationService service) {
        this.service = service;
    }

    @KafkaListener(topics = "payments")
    public void listen(PaymentDTO message) {
        LOGGER.info("Received message: {}", message);

        if(message.getStatus().equals(PaymentStatusDTO.ACCEPTED)) {
            service.changeStatus(message.getId(), Status.FINISHED);

        } else if(message.getStatus().equals(PaymentStatusDTO.IN_PROGRESS)) {
            service.changeStatus(message.getId(), Status.IN_PROGRESS);
        }

    }
}