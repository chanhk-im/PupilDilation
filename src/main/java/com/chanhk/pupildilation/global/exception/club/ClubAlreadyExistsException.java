package com.chanhk.pupildilation.global.exception.club;

import com.chanhk.pupildilation.global.exception.BusinessException;
import com.chanhk.pupildilation.global.exception.ErrorCode;

public class ClubAlreadyExistsException extends BusinessException {
    public ClubAlreadyExistsException() {
        super(ErrorCode.CLUB_ALREADY_EXISTS);
    }
}
