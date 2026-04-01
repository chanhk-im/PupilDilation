package com.chanhk.pupildilation.global.exception.reservation;

import com.chanhk.pupildilation.global.exception.BusinessException;
import com.chanhk.pupildilation.global.exception.ErrorCode;

public class ReservationAlreadyCancelledException extends BusinessException {
    public ReservationAlreadyCancelledException() {
        super(ErrorCode.RESERVATION_ALREADY_CANCELLED);
    }

}
