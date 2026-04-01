package com.chanhk.pupildilation.auth.dto.request;

import com.chanhk.pupildilation.global.common.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LoginRequest(
        @NotBlank
        @Pattern(
        regexp = Constants.EMAIL_REGX_STRING,
        message = Constants.EMAIL_VALID_MESSAGE)
        String email,
        @NotBlank String password
) {
}
