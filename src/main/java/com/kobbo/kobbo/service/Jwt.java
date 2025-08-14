package com.kobbo.kobbo.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

public class Jwt {
    private final Claims claims;
    private final SecretKey secretKey;

    public Jwt(Claims claims, SecretKey secretKey) {
        this.claims = claims;
        this.secretKey = secretKey;
    }

    public boolean isExpired() {
        return claims.getExpiration().before(new Date());
    }

    public UUID getUserId() {
        return UUID.fromString(claims.getSubject());
    }

    // Accès direct et sécurisé au claim hasSociete
    public boolean hasSociete() {
        // évite les NPE si le claim est absent
        return Boolean.TRUE.equals(claims.get("hasSociete", Boolean.class));
    }

    public UUID getCompteId() {
        String compteIdStr = claims.get("compteId", String.class);
        // évite les NPE si le claim est absent
        return compteIdStr != null ? UUID.fromString(compteIdStr) : null;
    }

    public String getRole() {
        return claims.get("role", String.class);
    }

    public String toString() {
        return Jwts.builder().claims(claims).signWith(secretKey).compact();
    }
}
