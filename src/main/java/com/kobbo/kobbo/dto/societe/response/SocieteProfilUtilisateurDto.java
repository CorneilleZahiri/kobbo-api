package com.kobbo.kobbo.dto.societe.response;

import com.kobbo.kobbo.dto.profilUtilisateur.response.ProfilUtilisateurResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@Builder
public class SocieteProfilUtilisateurDto {
    private SocieteDto societeDto;
    private Page<ProfilUtilisateurResponse> profilUtilisateurResponsePage;
}
