package joo.example.springboot2security.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.Keys;
import joo.example.springboot2security.dto.JwtProperties;
import joo.example.springboot2security.dto.MemberPrincipal;
import joo.example.springboot2security.exception.AuthException;
import joo.example.springboot2security.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenProvider {
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NICKNAME = "nickname";
    private static final String KEY_SPEC = "spec";
    private static final String ANONYMOUS = "ANONYMOUS";
    private final JwtProperties jwtProperties;

    public String generateAccessToken(String email, String nickname, String spec) {
        Map<String, String> claims = new HashMap<>();
        claims.put(KEY_EMAIL, email);
        claims.put(KEY_NICKNAME, nickname);
        claims.put(KEY_SPEC, spec); // "id:role"

        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.accessExpiredMs()))
                .signWith(getKey(jwtProperties.secretKey()))
                .compact();
    }


    /**
     * 토큰을 파싱하는 과정에서 validation도 같이 진행됨 (empty, expired 등)
     */
    public Claims parseOrValidateClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getKey(jwtProperties.secretKey()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new AuthException(ErrorCode.EXPIRED_TOKEN, e);
        }
    }

    /**
     * email과 role을 파싱하여 배열 형식으로 반환
     * @return {id, email, nickname, authority}
     */
    public String[] parseSpecification(Claims claims) {
        try {
            String[] spec = claims.get(TokenProvider.KEY_SPEC, String.class).split(":");
            return new String[] {
                spec[0], // id
                claims.get(TokenProvider.KEY_EMAIL, String.class),
                claims.get(TokenProvider.KEY_NICKNAME, String.class),
                spec[1] // authority
            };
        } catch (RequiredTypeException e) {
            return null;
        }
    }

    /**
     * access token의 형식이 잘못되었을 경우 ANONYMOUS로 반환
     */
    public MemberPrincipal getUserDetails(Claims claims) {
        String[] parsed = Optional.ofNullable(claims)
                .map(this::parseSpecification)
                .orElse(new String[] {null, ANONYMOUS, ANONYMOUS, ANONYMOUS});

        return MemberPrincipal.of(Long.parseLong(parsed[0]), parsed[1], parsed[2], parsed[3]); //id, email, nickname, authority
    }

    private SecretKey getKey(String key) {
        return Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }
}
