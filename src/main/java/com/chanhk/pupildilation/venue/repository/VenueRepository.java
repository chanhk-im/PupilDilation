package com.chanhk.pupildilation.venue.repository;

import com.chanhk.pupildilation.venue.domain.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Long> {
}
