package com.kobbo.kobbo.dto.societe.response;

import com.kobbo.kobbo.dto.tiers.response.TiersResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@Builder
public class SocieteTiersDto {
    private SocieteDto societeDto;
    private Page<TiersResponse> tiersResponsePage;
}
