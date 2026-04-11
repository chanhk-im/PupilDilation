package com.chanhk.pupildilation.global.exception.club;

import com.chanhk.pupildilation.global.exception.BusinessException;
import com.chanhk.pupildilation.global.exception.ErrorCode;

public class ClubNotFoundException extends BusinessException {
    public ClubNotFoundException() {
        super(ErrorCode.CLUB_NOT_FOUND);
    }
}
