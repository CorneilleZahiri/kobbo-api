package com.kobbo.kobbo.dto.responsable.response;

import com.kobbo.kobbo.dto.societe.response.SocieteDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class ResponsableDto {
    private UUID id;
    private String nom;
    private String fonction;
    private SocieteDto societeDto;
}
