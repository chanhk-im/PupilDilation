package com.chanhk.pupildilation.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C00", "입력값이 올바르지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C01", "서버 오류가 발생했습니다."),
    MISSING_HEADER(HttpStatus.BAD_REQUEST, "C02", "필수 헤더가 누락되었습니다."),
    MISSING_COOKIE(HttpStatus.BAD_REQUEST, "C03", "필수 쿠키가 누락되었습니다."),

    // jwt
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "J01", "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "J02", "만료된 토큰입니다."),

    // User
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "U01", "이미 존재하는 사용자입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U02", "존재하지 않는 사용자입니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "U03", "비밀번호가 일치하지 않습니다."),
    // EventSeat
    SEAT_NOT_AVAILABLE(HttpStatus.CONFLICT, "ES01", "이미 예매된 좌석입니다."),
    SEAT_NOT_CANCELLABLE(HttpStatus.CONFLICT, "ES02", "예매된 좌석이 아닙니다."),

    // Reservation
    RESERVATION_NOT_CONFIRMABLE(HttpStatus.CONFLICT, "RS01", "결제 대기 중인 상태가 아닙니다."),
    RESERVATION_ALREADY_CANCELLED(HttpStatus.CONFLICT, "RS02", "이미 취소된 예매입니다."),

    // Payment
    PAYMENT_NOT_APPROVABLE(HttpStatus.CONFLICT, "P01", "결제 승인을 처리할 수 없습니다."),
    PAYMENT_NOT_FAILABLE(HttpStatus.CONFLICT, "P02", "결제 취소 요청을 처리할 수 없습니다."),

    // Refund
    REFUND_NOT_APPROVABLE(HttpStatus.CONFLICT, "RF01", "환불 승인을 처리할 수 없습니다."),
    REFUND_NOT_FAILABLE(HttpStatus.CONFLICT, "RF02", "환불 취소 요청을 처리할 수 없습니다."),

    // Club
    CLUB_NOT_FOUND(HttpStatus.NOT_FOUND, "CL01", "존재하지 않는 동아리입니다."),
    CLUB_INVALID_INPUT(HttpStatus.BAD_REQUEST, "CL02", "동아리 입력값이 올바르지 않습니다."),
    CLUB_ALREADY_EXISTS(HttpStatus.CONFLICT, "CL03", "이미 존재하는 동아리입니다."),

    // Venue
    VENUE_NOT_FOUND(HttpStatus.NOT_FOUND, "V01", "존재하지 않는 공연장입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
