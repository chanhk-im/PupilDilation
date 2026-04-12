package com.chanhk.pupildilation.venue.dto.response;

import com.chanhk.pupildilation.venue.domain.Venue;

public record VenueGetAllResponseElement(Long id, String name, String address) {
    public static VenueGetAllResponseElement from(Venue venue) {
        return new VenueGetAllResponseElement(venue.getId(), venue.getName(), venue.getAddress());
    }
}
