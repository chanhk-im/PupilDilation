package com.chanhk.pupildilation.venue.dto.response;

import com.chanhk.pupildilation.venue.domain.Venue;

public record VenueUpdateResponse(Long id, String name, String address) {
    public static VenueUpdateResponse from(Venue venue) {
        return new VenueUpdateResponse(venue.getId(), venue.getName(), venue.getAddress());
    }
}
