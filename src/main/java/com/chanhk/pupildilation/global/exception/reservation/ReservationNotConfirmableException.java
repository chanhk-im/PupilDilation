package com.chanhk.pupildilation.global.exception.reservation;

import com.chanhk.pupildilation.global.exception.BusinessException;
import com.chanhk.pupildilation.global.exception.ErrorCode;

public class ReservationNotConfirmableException extends BusinessException {
    public ReservationNotConfirmableException() {
        super(ErrorCode.RESERVATION_NOT_CONFIRMABLE);
    }

}
