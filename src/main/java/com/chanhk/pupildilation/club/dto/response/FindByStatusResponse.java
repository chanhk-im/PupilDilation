package com.chanhk.pupildilation.club.dto.response;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record FindByStatusResponse(@NotBlank List<FindByStatusResponseElement> clubs) {
}
