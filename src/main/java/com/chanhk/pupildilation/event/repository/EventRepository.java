package com.chanhk.pupildilation.event.repository;

import com.chanhk.pupildilation.event.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
