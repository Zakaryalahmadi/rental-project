package com.rental.rental.services;

import com.rental.rental.dto.request.RegisterRequestDto;
import com.rental.rental.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JWTService {
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public JWTService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    public String generateToken(User user) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .subject(user.getEmail())
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims)).getTokenValue();
    }

    public Jwt decodeToken(String token) {
        try {
            return jwtDecoder.decode(token);
        } catch (JwtException e) {
            throw new IllegalArgumentException("Invalid JWT token", e);
        }
    }
}
