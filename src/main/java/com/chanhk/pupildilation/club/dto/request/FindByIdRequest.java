package com.chanhk.pupildilation.club.dto.request;

import jakarta.validation.constraints.NotNull;

public record FindByIdRequest(@NotNull Long clubId) {
}
