package com.chanhk.pupildilation.reservationseat.repository;

import com.chanhk.pupildilation.reservationseat.domain.ReservationSeat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationSeatRepository extends JpaRepository<ReservationSeat, Long> {
}
