package com.chanhk.pupildilation.auth.dto.response;

public record LoginResponse(String accessToken, String refreshToken, String tokenType) {
}
