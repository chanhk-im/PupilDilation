package com.chanhk.pupildilation.club.dto.request;

import jakarta.validation.constraints.NotBlank;

public record FindByIdRequest(@NotBlank Long clubId) {
}
