package com.kobbo.kobbo.dto.societe.request;

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

    private String email;

    private String adresse;

    @NotBlank(message = "Le pays est vide.")
    private String pays;
}
