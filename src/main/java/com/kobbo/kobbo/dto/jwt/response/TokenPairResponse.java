package com.kobbo.kobbo.dto.jwt.response;

import com.kobbo.kobbo.service.Jwt;

public record TokenPairResponse(Jwt accessToken, Jwt refreshToken) {
}
