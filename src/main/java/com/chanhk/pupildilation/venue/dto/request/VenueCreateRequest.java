package com.chanhk.pupildilation.venue.dto.request;

import jakarta.validation.constraints.NotBlank;

public record VenueCreateRequest(@NotBlank String name, @NotBlank String address) {
}
