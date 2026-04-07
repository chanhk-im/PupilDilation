package com.chanhk.pupildilation.club.dto.response;

import com.chanhk.pupildilation.club.domain.Club;
import com.chanhk.pupildilation.global.common.ClubStatus;
import jakarta.validation.constraints.NotBlank;

public record FindAllResponseElement(
        @NotBlank Long userId,
        @NotBlank String name,
        @NotBlank String description,
        @NotBlank ClubStatus status
) {
    public static FindAllResponseElement from(Club club) {
        return new FindAllResponseElement(
                club.getUserId(),
                club.getName(),
                club.getDescription(),
                club.getStatus()
        );
    }
}
