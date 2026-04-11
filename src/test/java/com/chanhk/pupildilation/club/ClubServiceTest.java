package com.chanhk.pupildilation.club;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.chanhk.pupildilation.club.domain.Club;
import com.chanhk.pupildilation.club.dto.request.ClubCreateRequest;
import com.chanhk.pupildilation.club.dto.request.ClubFindByIdRequest;
import com.chanhk.pupildilation.club.dto.request.ClubFindByStatusRequest;
import com.chanhk.pupildilation.club.dto.response.ClubCreateResponse;
import com.chanhk.pupildilation.club.dto.response.FindAllResponse;
import com.chanhk.pupildilation.club.dto.response.FindByIdResponse;
import com.chanhk.pupildilation.club.dto.response.FindByStatusResponse;
import com.chanhk.pupildilation.club.repository.ClubRepository;
import com.chanhk.pupildilation.global.common.ClubStatus;
import com.chanhk.pupildilation.global.exception.club.ClubAlreadyExistsException;
import com.chanhk.pupildilation.global.exception.club.ClubInvalidInputException;
import com.chanhk.pupildilation.global.exception.club.ClubNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClubServiceTest {

    @InjectMocks
    private ClubService clubService;

    @Mock
    private ClubRepository clubRepository;

    // ===== create =====

    @Test
    void userId_clubName_description을_받아서_동아리를_생성한다() {
        // given
        ClubCreateRequest request = new ClubCreateRequest(1L, "재즈 동아리", "교내 재즈 동아리입니다.");
        given(clubRepository.existsByUserId(request.userId())).willReturn(false);
        Club club = Club.of(request.userId(), request.clubName(), request.description());
        given(clubRepository.save(any(Club.class))).willReturn(club);

        // when
        ClubCreateResponse response = clubService.create(request);

        // then
        assertThat(response.userId()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("재즈 동아리");
        assertThat(response.status()).isEqualTo(ClubStatus.PENDING);
    }

    @Test
    void userId가_null이면_동아리_생성_시_예외가_발생한다() {
        ClubCreateRequest request = new ClubCreateRequest(null, "재즈 동아리", "교내 재즈 동아리입니다.");

        assertThatThrownBy(() -> clubService.create(request))
                .isInstanceOf(ClubInvalidInputException.class);
    }

    @Test
    void clubName이_null이면_동아리_생성_시_예외가_발생한다() {
        ClubCreateRequest request = new ClubCreateRequest(1L, null, "교내 재즈 동아리입니다.");

        assertThatThrownBy(() -> clubService.create(request))
                .isInstanceOf(ClubInvalidInputException.class);
    }

    @Test
    void clubName이_blank이면_동아리_생성_시_예외가_발생한다() {
        ClubCreateRequest request = new ClubCreateRequest(1L, "   ", "교내 재즈 동아리입니다.");

        assertThatThrownBy(() -> clubService.create(request))
                .isInstanceOf(ClubInvalidInputException.class);
    }

    @Test
    void description이_null이면_동아리_생성_시_예외가_발생한다() {
        ClubCreateRequest request = new ClubCreateRequest(1L, "재즈 동아리", null);

        assertThatThrownBy(() -> clubService.create(request))
                .isInstanceOf(ClubInvalidInputException.class);
    }

    @Test
    void 이미_동아리를_생성한_userId로_생성하면_예외가_발생한다() {
        // given
        ClubCreateRequest request = new ClubCreateRequest(1L, "재즈 동아리", "교내 재즈 동아리입니다.");
        given(clubRepository.existsByUserId(request.userId())).willReturn(true);

        // when & then
        assertThatThrownBy(() -> clubService.create(request))
                .isInstanceOf(ClubAlreadyExistsException.class);
    }

    // ===== findById =====

    @Test
    void clubId로_동아리를_조회한다() {
        // given
        Club club = Club.of(1L, "재즈 동아리", "교내 재즈 동아리입니다.");
        given(clubRepository.findById(1L)).willReturn(Optional.of(club));

        // when
        FindByIdResponse response = clubService.findById(new ClubFindByIdRequest(1L));

        // then
        assertThat(response.userId()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("재즈 동아리");
    }

    @Test
    void clubId가_null이면_동아리_조회_시_예외가_발생한다() {
        assertThatThrownBy(() -> clubService.findById(new ClubFindByIdRequest(null)))
                .isInstanceOf(ClubInvalidInputException.class);
    }

    @Test
    void 존재하지_않는_clubId로_조회하면_예외가_발생한다() {
        // given
        given(clubRepository.findById(999L)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> clubService.findById(new ClubFindByIdRequest(999L)))
                .isInstanceOf(ClubNotFoundException.class);
    }

    // ===== findAll =====

    @Test
    void 전체_동아리_목록을_조회한다() {
        // given
        List<Club> clubs = List.of(
                Club.of(1L, "재즈 동아리", "교내 재즈 동아리입니다."),
                Club.of(2L, "밴드 동아리", "교내 밴드 동아리입니다.")
        );
        given(clubRepository.findAll()).willReturn(clubs);

        // when
        FindAllResponse response = clubService.findAll();

        // then
        assertThat(response.clubs().size()).isEqualTo(2);
    }

    // ===== findByStatus =====

    @Test
    void status로_동아리_목록을_조회한다() {
        // given
        List<Club> clubs = List.of(Club.of(1L, "재즈 동아리", "교내 재즈 동아리입니다."));
        given(clubRepository.findByStatus(ClubStatus.PENDING)).willReturn(Optional.of(clubs.get(0)));

        // when
        FindByStatusResponse response = clubService.findByStatus(new ClubFindByStatusRequest(ClubStatus.PENDING));

        // then
        assertThat(response.clubs().size()).isEqualTo(1);
    }

    @Test
    void status가_null이면_동아리_목록_조회_시_예외가_발생한다() {
        assertThatThrownBy(() -> clubService.findByStatus(new ClubFindByStatusRequest(null)))
                .isInstanceOf(ClubInvalidInputException.class);
    }
}