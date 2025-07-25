package com.kobbo.kobbo.dto.role.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RoleResponse {
    private Long id;
    private String libelle;
}