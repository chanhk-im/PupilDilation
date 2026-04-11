package com.chanhk.pupildilation.club.dto.response;

import com.chanhk.pupildilation.club.domain.Club;
import com.chanhk.pupildilation.global.common.ClubStatus;

public record ClubCreateResponse(
        Long userId,
        Long clubId,
        String name,
        String description,
        ClubStatus status
) {
    public static ClubCreateResponse from(Club club) {
        return new ClubCreateResponse(
                club.getUserId(),
                club.getId(),
                club.getName(),
                club.getDescription(),
                club.getStatus()
        );
    }
}
