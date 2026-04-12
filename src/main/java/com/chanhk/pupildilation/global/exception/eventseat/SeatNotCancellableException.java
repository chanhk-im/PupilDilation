package com.chanhk.pupildilation.global.exception.eventseat;

import com.chanhk.pupildilation.global.exception.BusinessException;
import com.chanhk.pupildilation.global.exception.ErrorCode;

public class SeatNotCancellableException extends BusinessException {
    public SeatNotCancellableException() {
        super(ErrorCode.SEAT_NOT_CANCELLABLE);
    }
}
