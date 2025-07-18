package com.kobbo.kobbo.dto.societe.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RegisterSocieteRequest {
    @NotBlank(message = "La raison sociale est vide.")
    private String raisonSociale;

    @NotBlank(message = "L'email est vide.")
    @Email(message = "Le format de l'adresse email est incorrecte.")
    private String email;

    private String adresse;
}
