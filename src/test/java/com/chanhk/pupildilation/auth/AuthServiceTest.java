package com.chanhk.pupildilation.auth;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

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
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private ClubRepository clubRepository;
    @Mock
    private JwtProvider jwtProvider;
    @Mock
    private RefreshTokenManager refreshTokenManager;
    @Mock
    private TokenBlacklistManager tokenBlacklistManager;
    @Mock
    private PasswordEncoder passwordEncoder;

    // ===== signup =====

    @Test
    void 이메일_비밀번호_이름_받아서_회원가입한다() {
        // given
        SignupRequest request = new SignupRequest("test@test.com", "password123!", "홍길동");
        given(userRepository.existsByEmail(request.email())).willReturn(false);
        given(passwordEncoder.encode(request.password())).willReturn("encodedPassword");

        User user = User.of(request.email(), "encodedPassword", request.name());
        given(userRepository.save(any(User.class))).willReturn(user);

        // when
        SignupResponse response = authService.signup(request);

        // then
        assertThat(response.email()).isEqualTo("test@test.com");
        assertThat(response.name()).isEqualTo("홍길동");
        assertThat(response.role()).isEqualTo(Role.STUDENT);
    }

    @Test
    void 이미_존재하는_이메일로_회원가입하면_예외가_발생한다() {
        // given
        SignupRequest request = new SignupRequest("test@test.com", "password123!", "홍길동");
        given(userRepository.existsByEmail(request.email())).willReturn(true);

        // when & then
        assertThatThrownBy(() -> authService.signup(request))
                .isInstanceOf(UserAlreadyExistsException.class);
    }

    // ===== login =====

    @Test
    void 이메일_비밀번호로_로그인한다() {
        // given
        LoginRequest request = new LoginRequest("test@test.com", "password123!");
        User user = User.of(request.email(), "encodedPassword", "홍길동");
        given(userRepository.findByEmail(request.email())).willReturn(Optional.of(user));
        given(passwordEncoder.matches(request.password(), user.getPassword())).willReturn(true);
        given(jwtProvider.generateAccessToken(any(), any())).willReturn("accessToken");
        given(jwtProvider.generateRefreshToken(any(), any())).willReturn("refreshToken");

        // when
        LoginResponse response = authService.login(request);

        // then
        assertThat(response.accessToken()).isEqualTo("accessToken");
        assertThat(response.tokenType()).isEqualTo("Bearer");
    }

    @Test
    void 존재하지_않는_이메일로_로그인하면_예외가_발생한다() {
        // given
        LoginRequest request = new LoginRequest("notexist@test.com", "password123!");
        given(userRepository.findByEmail(request.email())).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void 비밀번호가_틀리면_예외가_발생한다() {
        // given
        LoginRequest request = new LoginRequest("test@test.com", "wrongPassword!");
        User user = User.of(request.email(), "encodedPassword", "홍길동");
        given(userRepository.findByEmail(request.email())).willReturn(Optional.of(user));
        given(passwordEncoder.matches(request.password(), user.getPassword())).willReturn(false);

        // when & then
        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(InvalidPasswordException.class);
    }

    // ===== logout =====

    @Test
    void 로그아웃하면_리프레시토큰이_삭제되고_액세스토큰이_블랙리스트에_등록된다() {
        // given
        LogoutRequest request = new LogoutRequest("accessToken");
        given(jwtProvider.extractUserId(request.token())).willReturn(1L);
        given(jwtProvider.getRemainingExpiration(request.token())).willReturn(3600000L);

        // when
        authService.logout(request);

        // then
        then(refreshTokenManager).should().deleteToken(1L);
        then(tokenBlacklistManager).should().blacklistToken("accessToken", 3600000L);
    }

    // ===== clubSignup =====

    @Test
    void 동아리_이메일_비밀번호_클럽명_설명으로_동아리_가입요청을_한다() {
        // given
        ClubSignupRequest request = new ClubSignupRequest(
                "band@test.com", "password123!", "재즈 동아리", "교내 재즈 동아리입니다."
        );
        given(userRepository.existsByEmail(request.email())).willReturn(false);
        given(passwordEncoder.encode(request.password())).willReturn("encodedPassword");

        User user = User.of(request.email(), "encodedPassword", request.clubName(), Role.CLUB);
        given(userRepository.save(any(User.class))).willReturn(user);

        Club club = Club.of(user.getId(), request.clubName(), request.description());
        given(clubRepository.save(any(Club.class))).willReturn(club);

        // when
        ClubSignupResponse response = authService.clubSignup(request);

        // then
        assertThat(response.status()).isEqualTo(ClubStatus.PENDING);
        assertThat(response.message()).isEqualTo(AuthService.PENDING_MESSAGE);
    }

    @Test
    void 이미_존재하는_이메일로_동아리_가입요청하면_예외가_발생한다() {
        // given
        ClubSignupRequest request = new ClubSignupRequest(
                "band@test.com", "password123!", "재즈 동아리", "교내 재즈 동아리입니다."
        );
        given(userRepository.existsByEmail(request.email())).willReturn(true);

        // when & then
        assertThatThrownBy(() -> authService.clubSignup(request))
                .isInstanceOf(UserAlreadyExistsException.class);
    }

    // ===== reissue =====

    @Test
    void 리프레시토큰으로_액세스토큰을_재발급한다() {
        // given
        ReissueRequest request = new ReissueRequest("refreshToken");
        given(jwtProvider.validateToken(request.token())).willReturn(true);
        given(jwtProvider.extractUserId(request.token())).willReturn(1L);
        given(refreshTokenManager.exists(1L)).willReturn(true);
        given(jwtProvider.extractRole(request.token())).willReturn(Role.STUDENT);
        given(jwtProvider.generateAccessToken(1L, Role.STUDENT)).willReturn("newAccessToken");

        // when
        ReissueResponse response = authService.reissue(request);

        // then
        assertThat(response.token()).isEqualTo("newAccessToken");
        assertThat(response.tokenType()).isEqualTo("Bearer");
    }

    @Test
    void 만료된_리프레시토큰으로_재발급하면_예외가_발생한다() {
        // given
        ReissueRequest request = new ReissueRequest("expiredToken");
        given(jwtProvider.validateToken(request.token())).willReturn(false);

        // when & then
        assertThatThrownBy(() -> authService.reissue(request))
                .isInstanceOf(ExpiredTokenException.class);
    }

    @Test
    void 올바르지_않은_리프레시토큰으로_재발급하면_예외가_발생한다() {
        // given
        ReissueRequest request = new ReissueRequest("invalidToken");
        given(jwtProvider.validateToken(request.token())).willReturn(false);

        // when & then
        assertThatThrownBy(() -> authService.reissue(request))
                .isInstanceOf(ExpiredTokenException.class);
    }

    @Test
    void Redis에_리프레시토큰이_존재하지_않으면_예외가_발생한다() {
        // given
        ReissueRequest request = new ReissueRequest("refreshToken");
        given(jwtProvider.validateToken(request.token())).willReturn(true);
        given(jwtProvider.extractUserId(request.token())).willReturn(1L);
        given(refreshTokenManager.exists(1L)).willReturn(false);

        // when & then
        assertThatThrownBy(() -> authService.reissue(request))
                .isInstanceOf(InvalidTokenException.class);
    }
}