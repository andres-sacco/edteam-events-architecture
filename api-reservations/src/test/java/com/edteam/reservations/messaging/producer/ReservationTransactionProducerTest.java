package com.edteam.reservations.messaging.producer;

import com.edteam.reservations.dto.ReservationTransactionDTO;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Tags(@Tag("producer"))
@DisplayName("Check the logic associate it with the producer")
class ReservationTransactionProducerTest {

    @Mock
    private KafkaTemplate<String, ReservationTransactionDTO> kafkaTemplate;

    @InjectMocks
    private ReservationTransactionProducer producer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Tag("success-case")
    @DisplayName("should send an event")
    @Test
    void send_should_send_new_event() {
        ReservationTransactionDTO message = new ReservationTransactionDTO();
        message.setId(1L);

        producer.sendMessage(message);

        verify(kafkaTemplate, times(1)).send(eq("reservation-transactions"), eq(message));
    }
}
