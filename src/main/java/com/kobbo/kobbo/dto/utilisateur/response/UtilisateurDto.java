package com.kobbo.kobbo.dto.utilisateur.response;

import com.kobbo.kobbo.dto.profilUtilisateur.response.ProfilUtilisateurResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class UtilisateurDto {
    private UUID id;
    private String nom;
    private String email;
    private String motDePasse;
    private ProfilUtilisateurResponse profilUtilisateurResponse;
}
