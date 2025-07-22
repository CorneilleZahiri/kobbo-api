package com.kobbo.kobbo.dto.societe.response;

import com.kobbo.kobbo.dto.utilisateur.response.UtilisateurResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@Builder
public class SocieteUtilisateurDto {
    private SocieteDto societeDto;
    private Page<UtilisateurResponse> utilisateurResponsePage;
}
