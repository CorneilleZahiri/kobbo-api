package com.kobbo.kobbo.dto.societe.response;

import com.kobbo.kobbo.dto.nature.response.NatureResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@Builder
public class SocieteNatureDto {
    private SocieteDto societeDto;
    private Page<NatureResponse> natureResponse;
}
