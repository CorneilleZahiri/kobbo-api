package com.kobbo.kobbo.dto.profilUtilisateur.response;

import com.kobbo.kobbo.dto.societe.response.SocieteDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProfilUtilisateurDto {
    private Long id;
    private String libelle;
    private SocieteDto societeDto;
}
