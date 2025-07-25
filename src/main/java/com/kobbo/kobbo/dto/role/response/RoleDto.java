package com.kobbo.kobbo.dto.role.response;

import com.kobbo.kobbo.dto.societe.response.SocieteDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RoleDto {
    private Long id;
    private String libelle;
    private SocieteDto societeDto;
}
