package com.chanhk.pupildilation.eventseat.domain;

import com.chanhk.pupildilation.event.domain.Event;
import com.chanhk.pupildilation.global.BaseEntity;
import com.chanhk.pupildilation.global.common.EventSeatStatus;
import com.chanhk.pupildilation.global.exception.eventseat.SeatNotAvailableException;
import com.chanhk.pupildilation.global.exception.eventseat.SeatNotCancellableException;
import com.chanhk.pupildilation.seat.domain.Seat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "event_seats",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_event_seat_event_id_seat_id",
                        columnNames = {"event_id", "seat_id"}
                )
        }
)
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EventSeat extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @Column(nullable = false)
    private Integer price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EventSeatStatus status;

    public void reserve() {
        if (this.status != EventSeatStatus.AVAILABLE) {
            throw new SeatNotAvailableException();
        }
        this.status = EventSeatStatus.RESERVED;
    }

    public void cancel() {
        if (this.status != EventSeatStatus.RESERVED) {
            throw new SeatNotCancellableException();
        }
        this.status = EventSeatStatus.AVAILABLE;
    }
}
