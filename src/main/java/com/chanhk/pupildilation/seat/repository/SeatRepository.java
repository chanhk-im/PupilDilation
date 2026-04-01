package com.chanhk.pupildilation.seat.repository;

import com.chanhk.pupildilation.seat.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
