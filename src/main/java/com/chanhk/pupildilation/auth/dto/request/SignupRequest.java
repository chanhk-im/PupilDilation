package com.chanhk.pupildilation.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @NotBlank @Email String email,
        @NotBlank @Size(min = 8, max = 20, message = "비밀번호는 8~20자여야 합니다")
        String password,
        @NotBlank String name
) {
}
