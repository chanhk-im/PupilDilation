package com.chanhk.pupildilation.venue;

import com.chanhk.pupildilation.global.exception.venue.VenueNotFoundException;
import com.chanhk.pupildilation.seat.domain.Seat;
import com.chanhk.pupildilation.seat.repository.SeatRepository;
import com.chanhk.pupildilation.venue.domain.Venue;
import com.chanhk.pupildilation.venue.dto.request.VenueCreateRequest;
import com.chanhk.pupildilation.venue.dto.request.VenueDeleteRequest;
import com.chanhk.pupildilation.venue.dto.request.VenueGetByIdRequest;
import com.chanhk.pupildilation.venue.dto.request.VenueUpdateRequest;
import com.chanhk.pupildilation.venue.dto.response.VenueCreateResponse;
import com.chanhk.pupildilation.venue.dto.response.VenueGetAllResponseElement;
import com.chanhk.pupildilation.venue.dto.response.VenueGetAllResponse;
import com.chanhk.pupildilation.venue.dto.response.VenueGetByIdResponse;
import com.chanhk.pupildilation.venue.dto.response.VenueUpdateResponse;
import com.chanhk.pupildilation.venue.repository.VenueRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class VenueService {
    private final VenueRepository venueRepository;
    private final SeatRepository seatRepository;

    public VenueGetAllResponse getAllVenues() {
        return new VenueGetAllResponse(
                venueRepository.findAll().stream().map(VenueGetAllResponseElement::from).toList());
    }

    public VenueGetByIdResponse getVenueById(VenueGetByIdRequest request) {
        Venue venue = venueRepository.findById(request.id())
                .orElseThrow(VenueNotFoundException::new);

        List<Seat> seats = seatRepository.findAllByVenueId(request.id());
        return VenueGetByIdResponse.of(venue, seats);
    }

    @Transactional
    public VenueCreateResponse create(VenueCreateRequest request) {
        Venue venue = Venue.of(request.name(), request.address());
        venueRepository.save(venue);
        return VenueCreateResponse.from(venue);
    }

    @Transactional
    public VenueUpdateResponse update(VenueUpdateRequest request) {
        Venue venue = venueRepository.findById(request.id()).orElseThrow(VenueNotFoundException::new);
        venue.update(request.name(), request.address());

        return VenueUpdateResponse.from(venue);
    }

    @Transactional
    public void delete(VenueDeleteRequest request) {
        Venue venue = venueRepository.findById(request.id()).orElseThrow(VenueNotFoundException::new);
        venueRepository.delete(venue);
    }
}
