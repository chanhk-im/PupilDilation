package com.chanhk.pupildilation.auth;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.chanhk.pupildilation.auth.dto.request.ClubSignupRequest;
import com.chanhk.pupildilation.auth.dto.request.LoginRequest;
import com.chanhk.pupildilation.auth.dto.request.SignupRequest;
import com.chanhk.pupildilation.auth.dto.response.ClubSignupResponse;
import com.chanhk.pupildilation.auth.dto.response.LoginResponse;
import com.chanhk.pupildilation.auth.dto.response.SignupResponse;
import com.chanhk.pupildilation.config.TestSecurityConfig;
import com.chanhk.pupildilation.global.common.ClubStatus;
import com.chanhk.pupildilation.global.common.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(AuthController.class)
@Import(TestSecurityConfig.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthService authService;

    // ===== signup =====

    @Test
    void 회원가입_성공시_201을_반환한다() throws Exception {
        SignupRequest request = new SignupRequest("test@test.com", "password123!", "홍길동");
        given(authService.signup(any()))
                .willReturn(new SignupResponse(1L, "test@test.com", "홍길동", Role.STUDENT));

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void 이메일_없이_회원가입하면_400을_반환한다() throws Exception {
        SignupRequest request = new SignupRequest(null, "password123!", "홍길동");

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 비밀번호_없이_회원가입하면_400을_반환한다() throws Exception {
        SignupRequest request = new SignupRequest("test@test.com", null, "홍길동");

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 이름_없이_회원가입하면_400을_반환한다() throws Exception {
        SignupRequest request = new SignupRequest("test@test.com", "password123!", null);

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 비밀번호가_8자_미만이면_400을_반환한다() throws Exception {
        SignupRequest request = new SignupRequest("test@test.com", "pass1!", "홍길동");

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 비밀번호가_20자_초과면_400을_반환한다() throws Exception {
        SignupRequest request = new SignupRequest(
                "test@test.com",
                "password123456789012!",
                "홍길동"
        );

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    // ===== login =====

    @Test
    void 로그인_성공시_200과_쿠키를_반환한다() throws Exception {
        LoginRequest request = new LoginRequest("test@test.com", "password123!");
        given(authService.login(any())).willReturn(
                new LoginResponse("accessToken", "refreshToken", "Bearer"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(cookie().exists("refreshToken"))
                .andExpect(cookie().httpOnly("refreshToken", true))
                .andExpect(cookie().path("refreshToken", "/api/auth/reissue"));
    }

    // ===== logout =====

    @Test
    void 로그아웃_성공시_200과_쿠키_삭제를_반환한다() throws Exception {
        mockMvc.perform(post("/api/auth/logout")
                        .header("Authorization", "Bearer accessToken"))
                .andExpect(status().isOk())
                .andExpect(cookie().maxAge("refreshToken", 0));
    }

    @Test
    void Authorization_헤더_없이_로그아웃하면_400을_반환한다() throws Exception {
        mockMvc.perform(post("/api/auth/logout"))
                .andExpect(status().isBadRequest());
    }

    // ===== clubSignup =====

    @Test
    void 동아리_가입요청_성공시_201을_반환한다() throws Exception {
        ClubSignupRequest request = new ClubSignupRequest(
                "band@test.com", "password123!", "재즈 동아리", "교내 재즈 동아리입니다.");
        given(authService.clubSignup(any())).willReturn(
                new ClubSignupResponse(1L, ClubStatus.PENDING, AuthService.PENDING_MESSAGE));

        mockMvc.perform(post("/api/auth/club/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void 이메일_없이_동아리_가입요청하면_400을_반환한다() throws Exception {
        ClubSignupRequest request = new ClubSignupRequest(
                null, "password123!", "재즈 동아리", "교내 재즈 동아리입니다.");

        mockMvc.perform(post("/api/auth/club/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 비밀번호_없이_동아리_가입요청하면_400을_반환한다() throws Exception {
        ClubSignupRequest request = new ClubSignupRequest(
                "band@test.com", null, "재즈 동아리", "교내 재즈 동아리입니다.");

        mockMvc.perform(post("/api/auth/club/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 클럽명_없이_동아리_가입요청하면_400을_반환한다() throws Exception {
        ClubSignupRequest request = new ClubSignupRequest(
                "band@test.com", "password123!", null, "교내 재즈 동아리입니다.");

        mockMvc.perform(post("/api/auth/club/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 비밀번호가_8자_미만이면_동아리_가입요청시_400을_반환한다() throws Exception {
        ClubSignupRequest request = new ClubSignupRequest(
                "band@test.com", "pass1!", "재즈 동아리", "교내 재즈 동아리입니다.");

        mockMvc.perform(post("/api/auth/club/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 비밀번호가_20자_초과면_동아리_가입요청시_400을_반환한다() throws Exception {
        ClubSignupRequest request = new ClubSignupRequest(
                "band@test.com",
                "password123456789012!",
                "재즈 동아리",
                "교내 재즈 동아리입니다."
        );

        mockMvc.perform(post("/api/auth/club/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    // ===== reissue =====

    @Test
    void 리프레시_토큰_없이_재발급_요청하면_400을_반환한다() throws Exception {
        mockMvc.perform(post("/api/auth/reissue"))
                .andExpect(status().isBadRequest());
    }
}
