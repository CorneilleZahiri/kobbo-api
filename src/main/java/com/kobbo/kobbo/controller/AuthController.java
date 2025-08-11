package com.kobbo.kobbo.controller;

import com.kobbo.kobbo.config.JwtConfig;
import com.kobbo.kobbo.dto.jwt.request.LoginUtilisateurRequest;
import com.kobbo.kobbo.dto.jwt.response.JwtResponse;
import com.kobbo.kobbo.dto.utilisateur.response.UtilisateurDto;
import com.kobbo.kobbo.entity.Utilisateur;
import com.kobbo.kobbo.service.Jwt;
import com.kobbo.kobbo.service.JwtService;
import com.kobbo.kobbo.service.UtilisateurService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtConfig jwtConfig;
    private UtilisateurService utilisateurService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(
            @Valid @RequestBody LoginUtilisateurRequest request,
            HttpServletResponse response) {

        //NB: L'échec d'authentification doit renvoyer un 401 Unauthorized.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getMotDePasse()
                )
        );

        Utilisateur utilisateur = utilisateurService.getUtilisateurByEmail(request.getEmail());

        Jwt accessToken = jwtService.generateAccessToken(utilisateur);
        Jwt refreshToken = jwtService.generateRefreshToken(utilisateur);

        Cookie cookie = new Cookie("refreshToken", refreshToken.toString());
        cookie.setHttpOnly(true);
        cookie.setPath("/auth/refresh");
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());
        cookie.setSecure(true);
        response.addCookie(cookie);

        return ResponseEntity.ok(new JwtResponse(accessToken.toString()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(
            @CookieValue(value = "refreshToken") String refreshToken
    ) {
        Jwt jwt = jwtService.parseToken(refreshToken);

        if (jwt == null || jwt.isExpired()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Utilisateur utilisateur = utilisateurService.getUtilisateurById(jwt.getUserId());
        Jwt accessToken = jwtService.generateAccessToken(utilisateur);

        return ResponseEntity.ok(new JwtResponse(accessToken.toString()));
    }

    @GetMapping("/me")
    public ResponseEntity<UtilisateurDto> me() {
        return ResponseEntity.ok(utilisateurService.me());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handlerBadCredentialsException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
