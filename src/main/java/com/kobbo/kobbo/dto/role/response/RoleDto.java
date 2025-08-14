package com.kobbo.kobbo.dto.role.response;

import com.kobbo.kobbo.dto.societe.response.SocieteDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class RoleDto {
    private UUID id;
    private String libelle;
    private SocieteDto societe;
}
