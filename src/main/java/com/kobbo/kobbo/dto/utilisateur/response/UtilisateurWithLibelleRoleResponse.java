package com.kobbo.kobbo.dto.utilisateur.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class UtilisateurWithLibelleRoleResponse {
    private UUID id;
    private String nom;
    private String email;
    private String libelleRole;
}