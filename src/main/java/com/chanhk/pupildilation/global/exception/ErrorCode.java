package com.chanhk.pupildilation.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C00", "입력값이 올바르지 않습니다"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C01", "서버 오류가 발생했습니다."),

    // EventSeat
    SEAT_NOT_AVAILABLE(HttpStatus.CONFLICT, "ES01", "이미 예매된 좌석입니다."),
    SEAT_NOT_CANCELLABLE(HttpStatus.CONFLICT, "ES02", "예매된 좌석이 아닙니다."),

    // Reservation
    RESERVATION_NOT_CONFIRMABLE(HttpStatus.CONFLICT, "R01", "결제 대기 중인 상태가 아닙니다."),
    RESERVATION_ALREADY_CANCELLED(HttpStatus.CONFLICT, "R02", "이미 취소된 예매입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
