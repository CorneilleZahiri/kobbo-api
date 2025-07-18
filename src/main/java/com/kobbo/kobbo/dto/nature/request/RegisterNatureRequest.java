package com.kobbo.kobbo.dto.nature.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RegisterNatureRequest {
    @NotBlank(message = "L'intitulé est vide")
    private String intitule;
}
