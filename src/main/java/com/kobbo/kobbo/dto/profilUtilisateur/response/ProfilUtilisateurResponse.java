package com.kobbo.kobbo.dto.profilUtilisateur.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProfilUtilisateurResponse {
    private Long id;
    private String libelle;
}