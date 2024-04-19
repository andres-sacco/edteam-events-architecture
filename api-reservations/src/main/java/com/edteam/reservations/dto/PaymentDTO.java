package com.edteam.reservations.dto;

import java.util.Objects;

public class PaymentDTO {

    private Long id;

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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentDTO that = (PaymentDTO) o;
        return Objects.equals(id, that.id) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status);
    }

    @Override
    public String toString() {
        return "PaymentDTO{" +
                "id=" + id +
                ", status=" + status +
                '}';
    }
}
