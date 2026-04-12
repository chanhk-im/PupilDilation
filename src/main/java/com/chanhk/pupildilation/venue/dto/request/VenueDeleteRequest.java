package com.chanhk.pupildilation.venue.dto.request;

import jakarta.validation.constraints.NotNull;

public record VenueDeleteRequest(@NotNull Long id) {
}
