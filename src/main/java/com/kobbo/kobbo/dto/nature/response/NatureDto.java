package com.kobbo.kobbo.dto.nature.response;

import com.kobbo.kobbo.dto.societe.response.SocieteDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class NatureDto {
    private Long id;
    private String intitule;
    private SocieteDto societeDto;
}
