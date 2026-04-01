package com.chanhk.pupildilation.reservation.repository;

import com.chanhk.pupildilation.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
