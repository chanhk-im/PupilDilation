package com.chanhk.pupildilation.seat.repository;

import com.chanhk.pupildilation.seat.domain.Seat;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findAllByVenueId(Long id);
}
