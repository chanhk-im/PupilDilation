package com.chanhk.pupildilation.reservationseat.domain;


import com.chanhk.pupildilation.eventseat.domain.EventSeat;
import com.chanhk.pupildilation.global.BaseEntity;
import com.chanhk.pupildilation.reservation.domain.Reservation;
import jakarta.persistence.Entity;
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
        name = "reservation_seats",
        uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_reservation_seat_event_seat_id",
                columnNames = {"event_seat_id"}
        )
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationSeat extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_seat_id")
    private EventSeat eventSeat;
}
