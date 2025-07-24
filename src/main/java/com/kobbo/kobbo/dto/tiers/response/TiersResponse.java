package com.kobbo.kobbo.dto.tiers.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class TiersResponse {
    private UUID id;
    private String nom;
    private String nature;
    private String contact;
}