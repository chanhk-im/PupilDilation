package com.chanhk.pupildilation.club.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClubCreateRequest(@NotNull Long userId, @NotBlank String clubName, @NotBlank String description) {
}
