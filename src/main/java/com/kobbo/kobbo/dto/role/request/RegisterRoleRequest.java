package com.kobbo.kobbo.dto.role.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RegisterRoleRequest {
    @NotBlank(message = "Le libellé est vide")
    private String libelle;
}
