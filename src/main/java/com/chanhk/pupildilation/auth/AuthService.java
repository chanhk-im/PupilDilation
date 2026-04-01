package com.chanhk.pupildilation.auth;

import com.chanhk.pupildilation.auth.dto.request.ClubSignupRequest;
import com.chanhk.pupildilation.auth.dto.request.LoginRequest;
import com.chanhk.pupildilation.auth.dto.request.LogoutRequest;
import com.chanhk.pupildilation.auth.dto.request.ReissueRequest;
import com.chanhk.pupildilation.auth.dto.request.SignupRequest;
import com.chanhk.pupildilation.auth.dto.response.ClubSignupResponse;
import com.chanhk.pupildilation.auth.dto.response.LoginResponse;
import com.chanhk.pupildilation.auth.dto.response.ReissueResponse;
import com.chanhk.pupildilation.auth.dto.response.SignupResponse;
import com.chanhk.pupildilation.club.domain.Club;
import com.chanhk.pupildilation.club.repository.ClubRepository;
import com.chanhk.pupildilation.global.common.ClubStatus;
import com.chanhk.pupildilation.global.common.Role;
import com.chanhk.pupildilation.global.exception.jwt.ExpiredTokenException;
import com.chanhk.pupildilation.global.exception.jwt.InvalidTokenException;
import com.chanhk.pupildilation.global.exception.user.InvalidPasswordException;
import com.chanhk.pupildilation.global.exception.user.UserAlreadyExistsException;
import com.chanhk.pupildilation.global.exception.user.UserNotFoundException;
import com.chanhk.pupildilation.global.jwt.JwtProvider;
import com.chanhk.pupildilation.global.jwt.RefreshTokenManager;
import com.chanhk.pupildilation.global.jwt.TokenBlacklistManager;
import com.chanhk.pupildilation.user.domain.User;
import com.chanhk.pupildilation.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class AuthService {
    public static final String RESPONSE_TOKEN_TYPE = "Bearer";
    public static final String PENDING_MESSAGE = "관리자 승인 대기 중입니다.";

    private final UserRepository userRepository;
    private final ClubRepository clubRepository;
    private final JwtProvider jwtProvider;
    private final RefreshTokenManager refreshTokenManager;
    private final TokenBlacklistManager tokenBlacklistManager;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignupResponse signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException();
        }

        String encodedPassword = passwordEncoder.encode(request.password());

        User user = User.of(request.email(), encodedPassword, request.name());

        userRepository.save(user);
        return new SignupResponse(user.getId(), user.getEmail(), user.getName(), user.getRole());

    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidPasswordException();
        }

        String accessToken = jwtProvider.generateAccessToken(user.getId(), user.getRole());
        String refreshToken = jwtProvider.generateRefreshToken(user.getId(), user.getRole());
        refreshTokenManager.save(refreshToken, user.getId());

        return new LoginResponse(accessToken, refreshToken, RESPONSE_TOKEN_TYPE);
    }

    public void logout(LogoutRequest request) {
        Long userId = jwtProvider.extractUserId(request.token());
        refreshTokenManager.deleteToken(userId);

        tokenBlacklistManager.blacklistToken(request.token(), jwtProvider.getRemainingExpiration(request.token()));
    }

    @Transactional
    public ClubSignupResponse clubSignup(ClubSignupRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException();
        }

        String encodedPassword = passwordEncoder.encode(request.password());

        User user = User.of(request.email(), encodedPassword, request.clubName(), Role.CLUB);
        userRepository.save(user);
        Club club = Club.of(user.getId(), request.clubName(), request.description());
        clubRepository.save(club);

        return new ClubSignupResponse(club.getId(), ClubStatus.PENDING, PENDING_MESSAGE);
    }

    public ReissueResponse reissue(ReissueRequest request) {
        if (!jwtProvider.validateToken(request.token())) {
            throw new ExpiredTokenException();
        }

        Long userId = jwtProvider.extractUserId(request.token());
        if (!refreshTokenManager.exists(userId)) {
            throw new InvalidTokenException();
        }

        Role role = jwtProvider.extractRole(request.token());
        String accessToken = jwtProvider.generateAccessToken(userId, role);
        return new ReissueResponse(accessToken, RESPONSE_TOKEN_TYPE);
    }
}
