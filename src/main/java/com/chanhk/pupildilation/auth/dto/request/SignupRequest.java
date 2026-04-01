package com.chanhk.pupildilation.auth.dto.request;

import com.chanhk.pupildilation.global.common.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @NotBlank
        @Pattern(
                regexp = Constants.EMAIL_REGX_STRING,
                message = Constants.EMAIL_VALID_MESSAGE)
        String email,
        @NotBlank @Size(min = 8, max = 20, message = "비밀번호는 8~20자여야 합니다")
        String password,
        @NotBlank String name
) {
}
