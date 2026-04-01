package com.chanhk.pupildilation.eventseat.domain;

import com.chanhk.pupildilation.global.common.EventSeatStatus;
import com.chanhk.pupildilation.global.exception.eventseat.SeatNotAvailableException;
import com.chanhk.pupildilation.global.exception.eventseat.SeatNotCancellableException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("EventSeat 엔터티 테스트")
class EventSeatTest {

    @Test
    void 예매_가능한_좌석은_예매_성공한다() {
        EventSeat seat = EventSeat.builder()
                .status(EventSeatStatus.AVAILABLE)
                .price(10000)
                .build();

        seat.reserve();

        assertThat(seat.getStatus()).isEqualTo(EventSeatStatus.RESERVED);
    }

    @Test
    void 이미_예매된_좌석은_예매_실패한다() {
        EventSeat seat = EventSeat.builder()
                .status(EventSeatStatus.RESERVED)
                .price(10000)
                .build();

        assertThatThrownBy(seat::reserve)
                .isInstanceOf(SeatNotAvailableException.class);
    }

    @Test
    void 예매된_좌석은_취소_성공한다() {
        EventSeat seat = EventSeat.builder()
                .status(EventSeatStatus.RESERVED)
                .price(10000)
                .build();

        seat.cancel();

        assertThat(seat.getStatus()).isEqualTo(EventSeatStatus.AVAILABLE);
    }

    @Test
    void 예매되지_않은_좌석은_취소_실패한다() {
        EventSeat seat = EventSeat.builder()
                .status(EventSeatStatus.AVAILABLE)
                .price(10000)
                .build();

        assertThatThrownBy(seat::cancel)
                .isInstanceOf(SeatNotCancellableException.class);
    }
}