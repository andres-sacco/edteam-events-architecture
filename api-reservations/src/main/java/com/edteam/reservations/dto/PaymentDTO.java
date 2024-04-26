package com.edteam.reservations.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Schema(description = "Information about the payment of a reservation")
public class PaymentDTO {

    @Schema(description = "Id of the reservation", example = "1", requiredMode = REQUIRED)
    private Long id;

    @Schema(description = "Status of the payment", example = "IN_PROGRESS", requiredMode = REQUIRED)
    private PaymentStatusDTO status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentStatusDTO getStatus() {
        return status;
    }

    public void setStatus(PaymentStatusDTO status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PaymentDTO that = (PaymentDTO) o;
        return Objects.equals(id, that.id) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status);
    }

    @Override
    public String toString() {
        return "PaymentDTO{" + "id=" + id + ", status=" + status + '}';
    }
}
