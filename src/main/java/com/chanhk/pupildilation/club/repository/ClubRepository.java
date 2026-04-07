package com.chanhk.pupildilation.club.repository;

import com.chanhk.pupildilation.club.domain.Club;
import com.chanhk.pupildilation.global.common.ClubStatus;
import java.util.Arrays;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long> {
    Optional<Club> findByStatus(ClubStatus status);
}
