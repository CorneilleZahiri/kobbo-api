package com.kobbo.kobbo.service;

import com.kobbo.kobbo.config.JwtConfig;
import com.kobbo.kobbo.entity.Utilisateur;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
public class JwtService {
    private final JwtConfig jwtConfig;

    public Jwt generateAccessToken(Utilisateur utilisateur, Boolean hasSociete,
                                   UUID compteId, String role) {
        Jwt jwt;
        if (!hasSociete) {
            jwt = generateToken(utilisateur, jwtConfig.getAccessTokenExpiration());
        } else {
            jwt = generateToken(utilisateur, jwtConfig.getAccessTokenExpiration(), compteId, role);
        }

        return jwt;
    }

    public Jwt generateRefreshToken(Utilisateur utilisateur, Boolean hasSociete,
                                    UUID compteId, String role) {
        Jwt jwt;
        if (!hasSociete) {
            jwt = generateToken(utilisateur, jwtConfig.getRefreshTokenExpiration());
        } else {
            jwt = generateToken(utilisateur, jwtConfig.getRefreshTokenExpiration(), compteId, role);
        }

        return jwt;
    }

    private Jwt generateToken(Utilisateur utilisateur, long tokenExpiration) {
        Claims claims = Jwts.claims()
                .subject(utilisateur.getId().toString())
                .add("email", utilisateur.getEmail())
                .add("nom", utilisateur.getNom())
                .add("hasSociete", false)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                .build();

        return new Jwt(claims, jwtConfig.getSecretKey());
    }

    //Surcharger la méthode
    private Jwt generateToken(Utilisateur utilisateur, long tokenExpiration,
                              UUID compteId, String role) {
        Claims claims = Jwts.claims()
                .subject(utilisateur.getId().toString())
                .add("email", utilisateur.getEmail())
                .add("nom", utilisateur.getNom())
                .add("hasSociete", true)
                .add("compteId", compteId)
                .add("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                .build();

        return new Jwt(claims, jwtConfig.getSecretKey());
    }

    public Jwt parseToken(String token) {
        try {
            Claims claims = getClaims(token);
            return new Jwt(claims, jwtConfig.getSecretKey());
        } catch (JwtException e) {
            return null;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
