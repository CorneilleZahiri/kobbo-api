package com.kobbo.kobbo.dto.comptes.response;

import com.kobbo.kobbo.dto.role.response.RoleDto;
import com.kobbo.kobbo.dto.utilisateur.response.UtilisateurDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CompteDto {
    private UUID id;
    private Boolean actif;
    private UtilisateurDto utilisateurDto;
    private RoleDto roleDto;
}
