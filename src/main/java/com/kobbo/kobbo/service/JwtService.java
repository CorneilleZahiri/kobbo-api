package com.kobbo.kobbo.service;

import com.kobbo.kobbo.config.JwtConfig;
import com.kobbo.kobbo.entity.Utilisateur;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ClaimsBuilder;
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

        return generateToken(utilisateur, jwtConfig.getAccessTokenExpiration(), hasSociete, compteId, role);
    }

    public Jwt generateRefreshToken(Utilisateur utilisateur, Boolean hasSociete,
                                    UUID compteId, String role) {

        return generateToken(utilisateur, jwtConfig.getRefreshTokenExpiration(), hasSociete, compteId, role);
    }

    //Surcharger la méthode
    private Jwt generateToken(Utilisateur utilisateur, long tokenExpiration
            , Boolean hasSociete, UUID compteId, String role) {

        ClaimsBuilder claimsBuilder = Jwts.claims()
                .subject(utilisateur.getId().toString())
                .add("email", utilisateur.getEmail())
                .add("nom", utilisateur.getNom())
                .add("hasSociete", hasSociete)
                .add("jti", UUID.randomUUID().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration));

        // Ajouter les claims optionnels seulement si présents
        if (hasSociete && compteId != null && role != null) {
            claimsBuilder
                    .add("compteId", compteId.toString())
                    .add("role", role);
        }

        return new Jwt(claimsBuilder.build(), jwtConfig.getSecretKey());
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
