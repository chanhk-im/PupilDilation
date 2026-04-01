package com.chanhk.pupildilation.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClubSignupRequest(
        @NotBlank @Email String email,
        @NotBlank String password,
        @NotBlank String clubName,
        @NotBlank String description
) {
}
