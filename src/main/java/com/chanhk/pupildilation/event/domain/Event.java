package com.chanhk.pupildilation.event.domain;

import com.chanhk.pupildilation.club.domain.Club;
import com.chanhk.pupildilation.event.dto.request.EventUpdateRequest;
import com.chanhk.pupildilation.global.BaseEntity;
import com.chanhk.pupildilation.venue.domain.Venue;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "events")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Event extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDateTime eventAt;

    @Column(nullable = false)
    private LocalDateTime bookingOpenAt;

    @Column(nullable = false)
    private LocalDateTime bookingCloseAt;

    @Column(nullable = false)
    private Integer cancelDeadlineDays = 3;

    @Column(nullable = false)
    private Integer maxSeatsPerUser = 4;

    public void update(EventUpdateRequest request, Venue venue) {
        this.name = request.name();
        this.description = request.description();
        this.eventAt = request.eventAt();
        this.bookingOpenAt = request.bookingOpenAt();
        this.bookingCloseAt = request.bookingCloseAt();
        this.cancelDeadlineDays = request.cancelDeadlineDays();
        this.maxSeatsPerUser = request.maxSeatsPerUser();
        this.venue = venue;
    }
}
