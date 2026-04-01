package com.chanhk.pupildilation.global.exception.eventseat;

import com.chanhk.pupildilation.global.exception.BusinessException;
import com.chanhk.pupildilation.global.exception.ErrorCode;

public class SeatNotAvailableException extends BusinessException {
    public SeatNotAvailableException() {
        super(ErrorCode.SEAT_NOT_AVAILABLE);
    }
}
