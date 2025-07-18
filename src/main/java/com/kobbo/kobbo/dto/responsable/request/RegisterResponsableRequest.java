package com.kobbo.kobbo.dto.responsable.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RegisterResponsableRequest {
    @NotBlank(message = "Le nom est vide")
    private String nom;
    
    private String fonction;
}
