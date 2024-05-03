package com.edteam.reservations.messaging.consumer;

import com.edteam.reservations.dto.PaymentDTO;
import com.edteam.reservations.enums.APIError;
import com.edteam.reservations.exception.EdteamException;
import com.edteam.reservations.model.Status;
import com.edteam.reservations.service.ReservationService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tags(@Tag("consumer"))
@DisplayName("Check the logic associate it with the consumer")
class PaymentConsumerTest {

    @Mock
    private ReservationService service;

    @InjectMocks
    private PaymentConsumer paymentConsumer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Tag("success-case")
    @DisplayName("should process an accepted payment event")
    @Test
    void listen_should_process_payment_accepted_event() {

        // Given
        PaymentDTO message = new PaymentDTO();
        message.setId(1L);
        message.setStatus("ACCEPTED");

        // When
        paymentConsumer.listen(message);

        // Then
        verify(service, times(1)).changeStatus(1L, Status.FINISHED);
    }

    @Tag("success-case")
    @DisplayName("should process an in progress payment event")
    @Test
    void listen_should_process_payment_in_progress_event() {
        // Given
        PaymentDTO message = new PaymentDTO();
        message.setId(1L);
        message.setStatus("IN_PROGRESS");

        // When
        paymentConsumer.listen(message);

        // Then
        verify(service, times(1)).changeStatus(1L, Status.IN_PROGRESS);
    }

    @Tag("error-case")
    @DisplayName("should produce an error with a unknown status")
    @Test
    void listen_should_throw_an_error_with_unknown_status() {
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
