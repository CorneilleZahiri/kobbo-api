package com.kobbo.kobbo.controller;

import com.kobbo.kobbo.config.JwtConfig;
import com.kobbo.kobbo.dto.comptes.request.CompteRequest;
import com.kobbo.kobbo.dto.comptes.response.CompteDto;
import com.kobbo.kobbo.dto.comptes.response.CompteResponse;
import com.kobbo.kobbo.dto.jwt.response.JwtResponse;
import com.kobbo.kobbo.dto.jwt.response.TokenPairResponse;
import com.kobbo.kobbo.dto.societe.request.RegisterSocieteRequest;
import com.kobbo.kobbo.service.CompteService;
import com.kobbo.kobbo.service.Jwt;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/comptes")
@AllArgsConstructor
public class CompteController {
    private final CompteService compteService;
    private final JwtConfig jwtConfig;

    @PostMapping
    public ResponseEntity<CompteDto> createCompte(@Valid @RequestBody RegisterSocieteRequest request,
                                                  UriComponentsBuilder uriComponentsBuilder) {
        CompteDto compteDto = compteService.createCompte(request);

        URI location = uriComponentsBuilder.path("/comptes/{id}")
                .buildAndExpand(compteDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(compteDto);
    }

    @GetMapping
    public ResponseEntity<List<CompteResponse>> listCompte() {
        return ResponseEntity.ok(compteService.listCompte());
    }

    @PostMapping("/select")
    public ResponseEntity<JwtResponse> selectCompte(@Valid @RequestBody CompteRequest request,
                                                    HttpServletResponse response) {
        TokenPairResponse tokenPairResponse = compteService.selectCompte(request);

        Jwt refreshToken = tokenPairResponse.refreshToken();

        Cookie cookie = new Cookie("refreshToken", refreshToken.toString());
        cookie.setHttpOnly(true);
        cookie.setPath("/auth/refresh");
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());
        cookie.setSecure(true);
        response.addCookie(cookie);

        return ResponseEntity.ok().body(new JwtResponse(tokenPairResponse.accessToken().toString()));
    }
}
