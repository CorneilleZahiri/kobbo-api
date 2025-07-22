package com.kobbo.kobbo.dto.utilisateur.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UpdateUtilisateurRequest {
    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 150, message = "Le nom ne doit pas dépasser 150 caractères")
    private String nom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Le format de l'email est incorrect")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, message = "Le mot de passe doit avoir au moins 6 caractères")
    private String motDePasse;

    @NotNull(message = "Le profil est obligatoire")
    @Positive(message = "L'id du profil doit être positif")
    private Long profilUtilisateurId;
}
