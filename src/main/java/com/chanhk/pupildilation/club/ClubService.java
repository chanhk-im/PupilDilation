package com.chanhk.pupildilation.club;


import com.chanhk.pupildilation.club.domain.Club;
import com.chanhk.pupildilation.club.dto.request.ClubCreateRequest;
import com.chanhk.pupildilation.club.dto.request.FindByIdRequest;
import com.chanhk.pupildilation.club.dto.request.FindByStatusRequest;
import com.chanhk.pupildilation.club.dto.response.ClubCreateResponse;
import com.chanhk.pupildilation.club.dto.response.FindAllResponse;
import com.chanhk.pupildilation.club.dto.response.FindAllResponseElement;
import com.chanhk.pupildilation.club.dto.response.FindByIdResponse;
import com.chanhk.pupildilation.club.dto.response.FindByStatusResponse;
import com.chanhk.pupildilation.club.dto.response.FindByStatusResponseElement;
import com.chanhk.pupildilation.club.repository.ClubRepository;
import com.chanhk.pupildilation.global.exception.club.ClubNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ClubService {
    private final ClubRepository clubRepository;

    public FindByIdResponse findById(FindByIdRequest request) {
        Club club = clubRepository.findById(request.clubId())
                .orElseThrow(ClubNotFoundException::new);

        return FindByIdResponse.from(club);
    }

    public FindAllResponse findAll() {
        return new FindAllResponse(clubRepository.findAll().stream()
                .map(FindAllResponseElement::from)
                .toList());
    }

    public FindByStatusResponse findByStatus(FindByStatusRequest request) {
        return new FindByStatusResponse(clubRepository.findByStatus(request.status()).stream()
                .map(FindByStatusResponseElement::from)
                .toList());
    }

    @Transactional
    public ClubCreateResponse create(ClubCreateRequest request) {
        Club club = Club.of(request.userId(), request.clubName(), request.description());
        clubRepository.save(club);
        return ClubCreateResponse.from(club);
    }
}
