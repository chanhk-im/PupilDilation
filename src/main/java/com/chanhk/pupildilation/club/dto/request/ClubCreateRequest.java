package com.chanhk.pupildilation.club.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ClubCreateRequest(@NotBlank Long userId, @NotBlank String clubName, @NotBlank String description) {
}
