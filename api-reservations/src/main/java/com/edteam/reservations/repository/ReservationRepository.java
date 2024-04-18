package com.edteam.reservations.repository;

import com.edteam.reservations.model.Reservation;
import com.edteam.reservations.model.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Transactional(readOnly = true, timeout = 30)
    List<Reservation> findAll(Specification<Reservation> specification, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Reservation e SET e.status = ?1 WHERE e.id = ?2")
    void updateStatusById(Long id, Status status);
}