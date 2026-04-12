package com.chanhk.pupildilation.auth;

import com.chanhk.pupildilation.auth.dto.request.ClubSignupRequest;
import com.chanhk.pupildilation.auth.dto.request.LoginRequest;
import com.chanhk.pupildilation.auth.dto.request.LogoutRequest;
import com.chanhk.pupildilation.auth.dto.request.ReissueRequest;
import com.chanhk.pupildilation.auth.dto.request.SignupRequest;
import com.chanhk.pupildilation.auth.dto.response.ClubSignupResponse;
import com.chanhk.pupildilation.auth.dto.response.LoginApiResponse;
import com.chanhk.pupildilation.auth.dto.response.LoginResponse;
import com.chanhk.pupildilation.auth.dto.response.ReissueResponse;
import com.chanhk.pupildilation.auth.dto.response.SignupResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    public static final int BEARER_PREFIX_LENGTH = 7;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody @Valid final SignupRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginApiResponse> login(@RequestBody @Valid final LoginRequest request) {
        LoginResponse result = authService.login(request);
        ResponseCookie cookie = ResponseCookie.from("refreshToken", result.refreshToken())
                .httpOnly(true)
                .path("/api/auth/reissue")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new LoginApiResponse(result.accessToken(), result.tokenType()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") final String authHeader) {
        String token = authHeader.substring(BEARER_PREFIX_LENGTH);
        authService.logout(new LogoutRequest(token));

        ResponseCookie deleteCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .path("/api/auth/reissue")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                .build();
    }

    @PostMapping("/club/signup")
    public ResponseEntity<ClubSignupResponse> clubSignup(@RequestBody @Valid final ClubSignupRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.clubSignup(request));
    }

    @PostMapping("/reissue")
    public ResponseEntity<ReissueResponse> reissue(@CookieValue("refreshToken") final String token) {
        return ResponseEntity.ok().body(authService.reissue(new ReissueRequest(token)));
    }
}
