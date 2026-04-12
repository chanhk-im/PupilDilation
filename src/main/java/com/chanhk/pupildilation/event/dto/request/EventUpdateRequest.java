package com.chanhk.pupildilation.event.dto.request;

import com.chanhk.pupildilation.club.domain.Club;
import com.chanhk.pupildilation.venue.domain.Venue;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

public record EventUpdateRequest(
        Long venueId,
        String name,
        String description,
        LocalDateTime eventAt,
        LocalDateTime bookingOpenAt,
        LocalDateTime bookingCloseAt,
        Integer cancelDeadlineDays,
        Integer maxSeatsPerUser) {

}
