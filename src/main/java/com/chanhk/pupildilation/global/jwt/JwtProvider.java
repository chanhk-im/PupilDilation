package com.chanhk.pupildilation.global.jwt;

import com.chanhk.pupildilation.global.common.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-expiration}")
    private Long accessExpiration;

    @Value("${jwt.refresh-expiration}")
    private Long refreshExpiration;
    private static final String REFRESH_TOKEN_PREFIX = "refresh: ";

    public String generateAccessToken(Long userId, Role role) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + accessExpiration);

        return generateToken(userId, role, expiration);
    }

    public String generateRefreshToken(Long userId, Role role) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + refreshExpiration);

        return generateToken(userId, role, expiration);
    }

    private String generateToken(Long userId, Role role, Date expiration) {
        Date now = new Date();
        SecretKey jwtSecretKey = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("role", role.name())
                .issuedAt(now)
                .expiration(expiration)
                .signWith(jwtSecretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        SecretKey jwtSecretKey = Keys.hmacShaKeyFor(secret.getBytes());

        try {
            Jwts.parser().verifyWith(jwtSecretKey).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Long extractUserId(String token) {
        return Long.parseLong(getClaims(token).getSubject());
    }

    public Role extractRole(String token) {
        return Role.valueOf(getClaims(token).get("role", String.class));
    }

    private Claims getClaims(String token) {
        SecretKey jwtSecretKey = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.parser()
                .verifyWith(jwtSecretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
