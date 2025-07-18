package com.kobbo.kobbo.dto.societe.response;

import com.kobbo.kobbo.dto.responsable.response.ResponsableResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@Builder
public class SocieteResponsableDto {
    private SocieteDto societeDto;
    private Page<ResponsableResponse> responsableResponsePage;
}
