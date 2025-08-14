package com.kobbo.kobbo.dto.comptes.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CompteRequest {
    @NotNull(message = "L'id du compte est obligatoire")
    private UUID id;
}
