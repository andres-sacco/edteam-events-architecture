package com.edteam.reservations.messaging;

import com.edteam.reservations.dto.PaymentDTO;
import com.edteam.reservations.enums.APIError;
import com.edteam.reservations.messaging.consumer.ReservationTransactionConsumer;
import com.edteam.reservations.model.Reservation;
import com.edteam.reservations.model.Status;
import com.edteam.reservations.repository.ReservationRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class APITest {

    static final String TOPIC = "payments";

    static DockerComposeContainer dockerComposeContainer = new DockerComposeContainer(
            new File("src/test/resources/docker/docker-compose.yml"))
                    .waitingFor("schema-registry",
                            Wait.forLogMessage(".*Server started, listening for requests.*\\n", 1))
                    .waitingFor("api-reservation-db",
                            Wait.forLogMessage(".*MySQL init process done. Ready for start up.*\\n", 1))
                    .waitingFor("kafka-init", Wait.forLogMessage(".*topic reservation-transactions was create.*\\n", 1))
                    .withLocalCompose(true);

    @Autowired
    KafkaTemplate<String, PaymentDTO> kafkaPaymentTemplate;

    @Autowired
    ReservationTransactionConsumer consumer;

    @Autowired
    ReservationRepository repository;

    @BeforeAll
    static void setUp() {
        dockerComposeContainer.start();
    }

    @AfterAll
    static void tearDown() {
        dockerComposeContainer.stop();
    }

    @Test
    void test() throws InterruptedException {
        // Given
        PaymentDTO payment = new PaymentDTO(1L, "ACCEPTED");

        // When
        kafkaPaymentTemplate.send(TOPIC, payment);

        // Then
        boolean messageConsumed = consumer.getLatch().await(10, TimeUnit.SECONDS);

        Optional<Reservation> result = repository.findById(1L);

        assertAll(
                () -> assertTrue(messageConsumed),
                () -> assertThat(consumer.getPayload(), containsString("{\"id\": 1, \"status\": \"FINISHED\"}")),
                () -> assertTrue(result.isPresent()),
                () -> assertEquals(Status.FINISHED, result.get().getStatus()));
    }

}
