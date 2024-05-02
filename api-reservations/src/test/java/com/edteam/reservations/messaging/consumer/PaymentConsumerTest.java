package com.edteam.reservations.messaging.consumer;

import com.edteam.reservations.dto.PaymentDTO;
import com.edteam.reservations.enums.APIError;
import com.edteam.reservations.exception.EdteamException;
import com.edteam.reservations.model.Status;
import com.edteam.reservations.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PaymentConsumerTest {

    @Mock
    private ReservationService service;

    @InjectMocks
    private PaymentConsumer paymentConsumer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testListen_AcceptedStatus() {

        // Given
        PaymentDTO message = new PaymentDTO();
        message.setId(1L);
        message.setStatus("ACCEPTED");

        // When
        paymentConsumer.listen(message);

        // Then
        verify(service, times(1)).changeStatus(1L, Status.FINISHED);
    }

    @Test
    void testListen_InProgressStatus() {
        // Given
        PaymentDTO message = new PaymentDTO();
        message.setId(1L);
        message.setStatus("IN_PROGRESS");

        // When
        paymentConsumer.listen(message);

        // Then
        verify(service, times(1)).changeStatus(1L, Status.IN_PROGRESS);
    }

    @Test
    void testListen_BadFormatStatus() {
        // Given
        PaymentDTO message = new PaymentDTO();
        message.setId(1L);
        message.setStatus("UNKNOWN_STATUS");

        // When
        EdteamException exception = assertThrows(EdteamException.class, () -> {
            paymentConsumer.listen(message);
        });

        // Then
        verify(service, never()).changeStatus(any(), any());

        assertAll(() -> assertNotNull(exception),
                () -> assertEquals(APIError.BAD_FORMAT.getMessage(), exception.getDescription()),
                () -> assertEquals(APIError.BAD_FORMAT.getHttpStatus(), exception.getStatus()));
    }
}
