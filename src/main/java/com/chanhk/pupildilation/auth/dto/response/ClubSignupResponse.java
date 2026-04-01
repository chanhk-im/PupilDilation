package com.chanhk.pupildilation.auth.dto.response;

import com.chanhk.pupildilation.global.common.ClubStatus;

public record ClubSignupResponse(
        Long clubId,
        ClubStatus status,
        String message
) {
}
