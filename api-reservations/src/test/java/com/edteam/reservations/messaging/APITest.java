package com.edteam.reservations.messaging;

import com.edteam.reservations.messaging.util.BaseITest;
import com.edteam.reservations.dto.PaymentDTO;
import com.edteam.reservations.messaging.consumer.ReservationTransactionConsumer;
import com.edteam.reservations.model.Reservation;
import com.edteam.reservations.model.Status;
import com.edteam.reservations.repository.ReservationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tags(@Tag("integration"))
@DisplayName("Check the entire flow of the consumer")
class APITest extends BaseITest {

    static final String TOPIC = "payments";

    @Autowired
    KafkaTemplate<String, PaymentDTO> kafkaPaymentTemplate;

    @Autowired
    ReservationTransactionConsumer consumer;

    @Autowired
    ReservationRepository repository;

    @Tag("success-case")
    @DisplayName("should send an event and persist some changes")
    @Test
    void receive_should_process_and_create_new_event() throws InterruptedException {
        // Given
        PaymentDTO payment = new PaymentDTO(1L, "ACCEPTED");

        // When
        kafkaPaymentTemplate.send(TOPIC, payment);

        // Then
        boolean messageConsumed = consumer.getLatch().await(10, TimeUnit.SECONDS);

        Optional<Reservation> result = repository.findById(1L);

        assertAll(() -> assertTrue(messageConsumed),
                () -> assertThat(consumer.getPayload(), containsString("{\"id\": 1, \"status\": \"FINISHED\"}")),
                () -> assertTrue(result.isPresent()), () -> assertEquals(Status.FINISHED, result.get().getStatus()));
    }

}
