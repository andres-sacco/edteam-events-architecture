package com.edteam.reservations.messaging.producer;

import com.edteam.reservations.dto.ReservationTransactionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ReservationTransactionProducerTest {

    @Mock
    private KafkaTemplate<String, ReservationTransactionDTO> kafkaTemplate;

    @InjectMocks
    private ReservationTransactionProducer producer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSendMessage() {
        ReservationTransactionDTO message = new ReservationTransactionDTO();
        message.setId(1L);

        producer.sendMessage(message);

        verify(kafkaTemplate, times(1)).send(eq("reservation-transactions"), eq(message));
    }
}
