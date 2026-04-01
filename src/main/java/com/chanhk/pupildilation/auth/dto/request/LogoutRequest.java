package com.chanhk.pupildilation.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LogoutRequest(@NotBlank String token) {
}
