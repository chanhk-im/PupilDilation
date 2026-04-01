package com.chanhk.pupildilation.reservation.domain;

import com.chanhk.pupildilation.global.common.ReservationStatus;
import com.chanhk.pupildilation.global.exception.reservation.ReservationAlreadyCancelledException;
import com.chanhk.pupildilation.global.exception.reservation.ReservationNotConfirmableException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Reservation 엔터티 테스트")
class ReservationTest {

    @Test
    void 대기중인_예매는_확정_성공한다() {
        Reservation reservation = Reservation.builder()
                .build(); // status 기본값 PENDING

        reservation.confirm();

        assertThat(reservation.getStatus()).isEqualTo(ReservationStatus.CONFIRMED);
    }

    @Test
    void 대기중이_아닌_예매는_확정_실패한다() {
        Reservation reservation = Reservation.builder()
                .status(ReservationStatus.CONFIRMED)
                .build();

        assertThatThrownBy(reservation::confirm)
                .isInstanceOf(ReservationNotConfirmableException.class);
    }

    @Test
    void 대기중인_예매는_취소_성공한다() {
        Reservation reservation = Reservation.builder()
                .build(); // status 기본값 PENDING

        reservation.cancel();

        assertThat(reservation.getStatus()).isEqualTo(ReservationStatus.CANCELLED);
        assertThat(reservation.getCanceledAt()).isNotNull();
    }

    @Test
    void 확정된_예매는_취소_성공한다() {
        Reservation reservation = Reservation.builder()
                .status(ReservationStatus.CONFIRMED)
                .build();

        reservation.cancel();

        assertThat(reservation.getStatus()).isEqualTo(ReservationStatus.CANCELLED);
        assertThat(reservation.getCanceledAt()).isNotNull();
    }

    @Test
    void 이미_취소된_예매는_취소_실패한다() {
        Reservation reservation = Reservation.builder()
                .status(ReservationStatus.CANCELLED)
                .build();

        assertThatThrownBy(reservation::cancel)
                .isInstanceOf(ReservationAlreadyCancelledException.class);
    }
}