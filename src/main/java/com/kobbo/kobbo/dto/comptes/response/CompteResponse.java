package com.kobbo.kobbo.dto.comptes.response;

import com.kobbo.kobbo.dto.role.response.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CompteResponse {
    private UUID id;
    private Boolean actif;
    private RoleDto role;
}
