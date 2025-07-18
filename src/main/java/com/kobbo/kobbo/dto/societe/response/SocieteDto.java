package com.kobbo.kobbo.dto.societe.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class SocieteDto {
    private UUID id;
    private String raisonSociale;
    private String email;
    private String adresse;
}
