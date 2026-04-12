package com.chanhk.pupildilation.venue.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VenueUpdateRequest(@NotNull Long id, @NotBlank String name, @NotBlank String address) {
}
