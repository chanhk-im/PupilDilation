package com.chanhk.pupildilation.global.exception.venue;

import com.chanhk.pupildilation.global.exception.BusinessException;
import com.chanhk.pupildilation.global.exception.ErrorCode;

public class VenueNotFoundException extends BusinessException {
    public VenueNotFoundException() {
        super(ErrorCode.VENUE_NOT_FOUND);
    }
}
