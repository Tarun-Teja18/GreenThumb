package dev.tarun.greenthumb.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenProvider {

    private final SecretKey key;
    private final long validityMs;

    public TokenProvider(@Value("${jwt.secret}") String secret,
                         @Value("${jwt.expiration-ms}") long validityMs) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.validityMs = validityMs;
    }

    public String generateToken(Authentication authentication) {
        String roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date now = new Date();
        return Jwts.builder()
                .subject(authentication.getName())              // the email
                .claim("roles", roles)                          // their roles
                .issuedAt(now)
                .expiration(new Date(now.getTime() + validityMs))
                .signWith(key)                                  // sign → tamper-proof
                .compact();                                     // produce the string
    }
}