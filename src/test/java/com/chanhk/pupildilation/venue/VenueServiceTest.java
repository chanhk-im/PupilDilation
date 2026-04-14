package com.chanhk.pupildilation.venue;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.chanhk.pupildilation.global.exception.venue.VenueNotFoundException;
import com.chanhk.pupildilation.seat.repository.SeatRepository;
import com.chanhk.pupildilation.venue.domain.Venue;
import com.chanhk.pupildilation.venue.dto.request.VenueCreateRequest;
import com.chanhk.pupildilation.venue.dto.request.VenueDeleteRequest;
import com.chanhk.pupildilation.venue.dto.request.VenueGetByIdRequest;
import com.chanhk.pupildilation.venue.dto.request.VenueUpdateRequest;
import com.chanhk.pupildilation.venue.dto.response.VenueCreateResponse;
import com.chanhk.pupildilation.venue.dto.response.VenueGetAllResponse;
import com.chanhk.pupildilation.venue.dto.response.VenueGetByIdResponse;
import com.chanhk.pupildilation.venue.dto.response.VenueUpdateResponse;
import com.chanhk.pupildilation.venue.repository.VenueRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VenueServiceTest {
    @InjectMocks
    private VenueService venueService;

    @Mock
    private VenueRepository venueRepository;

    @Mock
    private SeatRepository seatRepository;

    // ===== getAllVenues =====

    @Test
    void 전체_공연장_목록을_조회한다() {
        // given
        Venue venue1 = Venue.of("올림픽 체조경기장", "서울시 강남구");
        Venue venue2 = Venue.of("잠실 실내체육관", "서울시 송파구");
        given(venueRepository.findAll()).willReturn(List.of(venue1, venue2));

        // when
        VenueGetAllResponse response = venueService.getAllVenues();

        // then
        assertThat(response.venues()).isNotNull();
        assertThat(response.venues().size()).isEqualTo(2);
        assertThat(response.venues().get(0).name()).isEqualTo("올림픽 체조경기장");
        assertThat(response.venues().get(1).name()).isEqualTo("잠실 실내체육관");
    }

    // ===== getVenueById =====

    @Test
    void venueId로_공연장을_조회한다() {
        // given
        long venueId = 1L;
        Venue venue = Venue.of("올림픽 체조경기장", "서울시 강남구");
        VenueGetByIdRequest request = new VenueGetByIdRequest(venueId);
        given(venueRepository.findById(venueId)).willReturn(Optional.of(venue));
        given(seatRepository.findAllByVenueId(venueId)).willReturn(List.of());

        // when
        VenueGetByIdResponse response = venueService.getVenueById(request);

        // then
        assertThat(response.name()).isEqualTo("올림픽 체조경기장");
    }

    @Test
    void 존재하지_않는_venueId로_조회하면_예외가_발생한다() {
        // given
        long venueId = 999L;
        VenueGetByIdRequest request = new VenueGetByIdRequest(venueId);
        given(venueRepository.findById(venueId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> venueService.getVenueById(request))
                .isInstanceOf(VenueNotFoundException.class);
    }

    // ===== create =====

    @Test
    void 공연장을_생성한다() {
        // given
        VenueCreateRequest request = new VenueCreateRequest("올림픽 체조경기장", "서울시 강남구");
        Venue savedVenue = Venue.of("올림픽 체조경기장", "서울시 강남구");
        given(venueRepository.save(any(Venue.class))).willReturn(savedVenue);

        // when
        VenueCreateResponse response = venueService.create(request);

        // then
        assertThat(response.name()).isEqualTo("올림픽 체조경기장");
        assertThat(response.address()).isEqualTo("서울시 강남구");
    }

    // ===== update =====

    @Test
    void 공연장_정보를_수정한다() {
        // given
        long venueId = 1L;
        Venue venue = Venue.of("올림픽 체조경기장", "서울시 강남구");
        VenueUpdateRequest request = new VenueUpdateRequest(venueId, "올림픽 펜싱경기장", "서울시 강남구");
        given(venueRepository.findById(venueId)).willReturn(Optional.of(venue));

        // when
        VenueUpdateResponse response = venueService.update(request);

        // then
        assertThat(response.name()).isEqualTo("올림픽 펜싱경기장");
    }

    @Test
    void 존재하지_않는_공연장을_수정하면_예외가_발생한다() {
        // given
        long venueId = 999L;
        VenueUpdateRequest request = new VenueUpdateRequest(venueId, "올림픽 펜싱경기장", "서울시 강남구");
        given(venueRepository.findById(venueId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> venueService.update(request))
                .isInstanceOf(VenueNotFoundException.class);
    }

    // ===== delete =====

    @Test
    void 공연장을_삭제한다() {
        // given
        long venueId = 1L;
        Venue venue = Venue.of("올림픽 체조경기장", "서울시 강남구");
        VenueDeleteRequest request = new VenueDeleteRequest(venueId);
        given(venueRepository.findById(venueId)).willReturn(Optional.of(venue));

        // when
        venueService.delete(request);

        // then
        verify(venueRepository).delete(venue);
    }

    @Test
    void 존재하지_않는_공연장을_삭제하면_예외가_발생한다() {
        // given
        long venueId = 999L;
        VenueDeleteRequest request = new VenueDeleteRequest(venueId);
        given(venueRepository.findById(venueId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> venueService.delete(request))
                .isInstanceOf(VenueNotFoundException.class);
    }
}
