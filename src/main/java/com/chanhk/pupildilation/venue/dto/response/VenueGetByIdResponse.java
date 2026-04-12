package com.chanhk.pupildilation.venue.dto.response;

import com.chanhk.pupildilation.global.common.SeatType;
import com.chanhk.pupildilation.seat.domain.Seat;
import com.chanhk.pupildilation.venue.domain.Venue;
import java.util.List;

public record VenueGetByIdResponse(Long id, String name, List<SeatElement> seats) {
    public record SeatElement(Long id, String section, Integer rowNum, Integer number, SeatType seatType) {
        public static SeatElement from(Seat seat) {
            return new SeatElement(seat.getId(), seat.getSection(), seat.getRowNum(), seat.getNumber(), seat.getSeatType());
        }
    }

    public static VenueGetByIdResponse of(Venue venue, List<Seat> seats) {
        List<SeatElement> seatItems = seats.stream().map(SeatElement::from).toList();
        return new VenueGetByIdResponse(venue.getId(), venue.getName(), seatItems);
    }
}
