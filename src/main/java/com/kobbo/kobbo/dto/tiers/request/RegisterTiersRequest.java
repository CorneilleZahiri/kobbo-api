package com.kobbo.kobbo.dto.tiers.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RegisterTiersRequest {
    @NotBlank(message = "Le nom est vide")
    private String nom;
    private String nature;
    private String contact;
}
