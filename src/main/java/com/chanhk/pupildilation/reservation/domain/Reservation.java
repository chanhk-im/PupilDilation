package com.chanhk.pupildilation.reservation.domain;

import com.chanhk.pupildilation.event.domain.Event;
import com.chanhk.pupildilation.global.BaseEntity;
import com.chanhk.pupildilation.global.common.ReservationStatus;
import com.chanhk.pupildilation.global.exception.reservation.ReservationAlreadyCancelledException;
import com.chanhk.pupildilation.global.exception.reservation.ReservationNotConfirmableException;
import com.chanhk.pupildilation.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "reservations")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Reservation extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReservationStatus status = ReservationStatus.PENDING;

    @Builder.Default
    @Column(nullable = false)
    private LocalDateTime reservedAt = LocalDateTime.now();

    private LocalDateTime canceledAt;

    public void confirm() {
        if (this.status != ReservationStatus.PENDING) {
            throw new ReservationNotConfirmableException();
        }
        this.status = ReservationStatus.CONFIRMED;
    }

    public void cancel() {
        if (this.status == ReservationStatus.CANCELLED) {
            throw new ReservationAlreadyCancelledException();
        }
        this.status = ReservationStatus.CANCELLED;
        this.canceledAt = LocalDateTime.now();
    }

}
