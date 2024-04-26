package com.edteam.reservations.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Schema(description = "Information about a transaction over a reservation")
public class ReservationTransactionDTO {

    @Schema(description = "Id of the reservation", example = "1", requiredMode = REQUIRED)
    private Long id;

    @Schema(description = "Status of the reservation", example = "FINISHED", requiredMode = REQUIRED)
    private StatusDTO status;

    public ReservationTransactionDTO() {
    }

    public ReservationTransactionDTO(Long id, StatusDTO status) {
        this.id = id;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ReservationTransactionDTO that = (ReservationTransactionDTO) o;
        return Objects.equals(id, that.id) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status);
    }

    @Override
    public String toString() {
        return "ReservationTransactionDTO{" + "id=" + id + ", status=" + status + '}';
    }
}
