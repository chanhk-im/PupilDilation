package com.chanhk.pupildilation.global.jwt;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.mock;

import com.chanhk.pupildilation.global.common.Role;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class JwtProviderTest {
    private JwtProvider jwtProvider;

    private static final String SECRET = "hehehehehehehehehehehehehehehehe";

    private static final Long ACCESS_EXPIRATION = 1000 * 60 * 60L;
    private static final Long REFRESH_EXPIRATION = 1000 * 60 * 60 * 24 * 7L;
    private static final Long USER_ID = 1L;
    private static final Role ROLE = Role.STUDENT;

    @BeforeEach
    void setUp() {
        RedisTemplate<String, String> redisTemplate = mock();
        jwtProvider = new JwtProvider(redisTemplate);
        ReflectionTestUtils.setField(jwtProvider, "secret", SECRET);
        ReflectionTestUtils.setField(jwtProvider, "accessExpiration", ACCESS_EXPIRATION);
        ReflectionTestUtils.setField(jwtProvider, "refreshExpiration", REFRESH_EXPIRATION);
    }

    @Test
    void 액새스_토큰_생성_후_검증_성공한다() {
        String token = jwtProvider.generateAccessToken(USER_ID, ROLE);

        assertThat(token).isNotNull();
        assertThat(jwtProvider.validateToken(token)).isTrue();
    }

    @Test
    void 리프래시_토큰_생성_후_검증_성공한다() {
        String token = jwtProvider.generateRefreshToken(USER_ID, ROLE);

        assertThat(token).isNotNull();
        assertThat(jwtProvider.validateToken(token)).isTrue();
    }

    @Test
    void 변조된_토큰은_검증_실패한다() {
        String token = jwtProvider.generateAccessToken(USER_ID, ROLE);
        String tampered = token + "tampered";

        assertThat(jwtProvider.validateToken(tampered)).isFalse();
    }

    @Test
    void 만료된_토큰은_검증_실패한다() {
        ReflectionTestUtils.setField(jwtProvider, "accessExpiration", 1L);
        String token = jwtProvider.generateAccessToken(USER_ID, ROLE);

        await().atMost(10, TimeUnit.MILLISECONDS);

        assertThat(jwtProvider.validateToken(token)).isFalse();
    }

    @Test
    void 액세스_토큰에서_User_ID_추출한다() {
        String token = jwtProvider.generateAccessToken(USER_ID, ROLE);
        Long userId = jwtProvider.extractUserId(token);

        assertThat(userId).isEqualTo(USER_ID);
    }

    @Test
    void 액세스_토큰에서_Role_추출한다() {
        String token = jwtProvider.generateAccessToken(USER_ID, ROLE);
        Role role = jwtProvider.extractRole(token);

        assertThat(role).isEqualTo(ROLE);
    }
}
