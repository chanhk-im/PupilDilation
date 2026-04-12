package com.chanhk.pupildilation.venue.dto.response;

import com.chanhk.pupildilation.venue.domain.Venue;

public record VenueCreateResponse(Long id, String name, String address) {
    public static VenueCreateResponse from(Venue venue) {
        return new VenueCreateResponse(venue.getId(), venue.getName(), venue.getAddress());
    }
}
