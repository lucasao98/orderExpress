package com.foody.delivery.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.foody.delivery.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${jwt.secret}")
    private String secretKey;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secretKey);
            return JWT.create().withIssuer("foody-delivery").withSubject(user.getEmail()).withExpiresAt(genExpirationDate()).sign(algorithm);

        } catch ( JWTVerificationException exception) {
            return "";
        }
    }

    public String checkToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secretKey);

            return JWT.require(algorithm).withIssuer("foody-delivery").build().verify(token).getSubject();
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
