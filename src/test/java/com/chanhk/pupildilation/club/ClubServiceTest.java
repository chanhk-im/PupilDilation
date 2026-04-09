package com.chanhk.pupildilation.club;

import com.chanhk.pupildilation.club.dto.request.ClubCreateRequest;
import com.chanhk.pupildilation.club.repository.ClubRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ClubServiceTest {
    @InjectMocks
    private ClubService clubService;

    @Mock
    private ClubRepository clubRepository;

    @Test
    void ID로_Club_찾는다() {
        ClubCreateRequest request = new ClubCreateRequest(1L, "재즈 동아리", "교내 재즈 동아리입니다.");

    }
}
