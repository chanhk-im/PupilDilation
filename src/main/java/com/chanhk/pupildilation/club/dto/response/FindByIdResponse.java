package com.chanhk.pupildilation.club.dto.response;

import com.chanhk.pupildilation.club.domain.Club;
import com.chanhk.pupildilation.global.common.ClubStatus;
import jakarta.validation.constraints.NotBlank;

public record FindByIdResponse(
        @NotBlank Long userId,
        @NotBlank String name,
        @NotBlank String description,
        @NotBlank ClubStatus status
) {
    public static FindByIdResponse from(Club club) {
        return new FindByIdResponse(
                club.getUserId(),
                club.getName(),
                club.getDescription(),
                club.getStatus()
        );
    }
}
