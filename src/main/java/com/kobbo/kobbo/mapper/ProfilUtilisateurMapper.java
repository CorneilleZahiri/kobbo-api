package com.kobbo.kobbo.mapper;

import com.kobbo.kobbo.dto.profilUtilisateur.request.RegisterProfilUtilisateurRequest;
import com.kobbo.kobbo.dto.profilUtilisateur.response.ProfilUtilisateurDto;
import com.kobbo.kobbo.dto.profilUtilisateur.response.ProfilUtilisateurResponse;
import com.kobbo.kobbo.entity.ProfilUtilisateur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProfilUtilisateurMapper {
    @Mapping(source = "societe.id", target = "societeDto.id")
    ProfilUtilisateurDto toDto(ProfilUtilisateur profilUtilisateur);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "societe", ignore = true)
    ProfilUtilisateur toEntity(RegisterProfilUtilisateurRequest profilUtilisateurRequest);

    ProfilUtilisateurResponse toProfilUtilisateurResponse(ProfilUtilisateur profilUtilisateur);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "societe", ignore = true)
    void update(RegisterProfilUtilisateurRequest profilUtilisateurRequest, @MappingTarget ProfilUtilisateur profilUtilisateur);
}
