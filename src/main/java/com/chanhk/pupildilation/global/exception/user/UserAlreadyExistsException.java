package com.chanhk.pupildilation.global.exception.user;

import com.chanhk.pupildilation.global.exception.BusinessException;
import com.chanhk.pupildilation.global.exception.ErrorCode;

public class UserAlreadyExistsException extends BusinessException {
    public UserAlreadyExistsException() {
        super(ErrorCode.SEAT_NOT_AVAILABLE);
    }

}
