package com.kobbo.kobbo.dto.tiers.response;

import com.kobbo.kobbo.dto.societe.response.SocieteDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class TiersDto {
    private UUID id;
    private String nom;
    private String nature;
    private String contact;
    private SocieteDto societeDto;
}
