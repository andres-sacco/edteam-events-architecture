package com.edteam.reservations.messaging.serializer;

import com.edteam.reservations.dto.ReservationTransactionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReservationSerializer implements Serializer<ReservationTransactionDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationSerializer.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, ReservationTransactionDTO data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            LOGGER.error("Occurs an error while serializing reservation data", e);
            throw new RuntimeException("Error serializing object: " + data, e);
        }
    }
}
