package com.kobbo.kobbo.dto.role.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class RoleResponse {
    private UUID id;
    private String libelle;
}