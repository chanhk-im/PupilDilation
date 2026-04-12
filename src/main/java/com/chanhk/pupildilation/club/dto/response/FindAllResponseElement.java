package com.chanhk.pupildilation.club.dto.response;

import com.chanhk.pupildilation.club.domain.Club;
import com.chanhk.pupildilation.global.common.ClubStatus;

public record FindAllResponseElement(
        Long userId,
        Long clubId,
        String name,
        String description,
        ClubStatus status
) {
    public static FindAllResponseElement from(Club club) {
        return new FindAllResponseElement(
                club.getUserId(),
                club.getId(),
                club.getName(),
                club.getDescription(),
                club.getStatus()
        );
    }
}
