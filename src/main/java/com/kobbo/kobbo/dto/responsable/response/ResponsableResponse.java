package com.kobbo.kobbo.dto.responsable.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class ResponsableResponse {
    private UUID id;
    private String nom;
    private String fonction;
}