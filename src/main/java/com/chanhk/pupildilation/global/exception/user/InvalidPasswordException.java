package com.chanhk.pupildilation.global.exception.user;

import com.chanhk.pupildilation.global.exception.BusinessException;
import com.chanhk.pupildilation.global.exception.ErrorCode;

public class InvalidPasswordException extends BusinessException {
    public InvalidPasswordException() {
        super(ErrorCode.INVALID_PASSWORD);
    }
}
