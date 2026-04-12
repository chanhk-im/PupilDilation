package com.chanhk.pupildilation.global.exception.club;

import com.chanhk.pupildilation.global.exception.BusinessException;
import com.chanhk.pupildilation.global.exception.ErrorCode;

public class ClubInvalidInputException extends BusinessException {
    public ClubInvalidInputException() {
        super(ErrorCode.CLUB_INVALID_INPUT);
    }
}
