package com.kobbo.kobbo.dto.profilUtilisateur.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RegisterProfilUtilisateurRequest {
    @NotBlank(message = "Le libellé est vide")
    private String libelle;
}
