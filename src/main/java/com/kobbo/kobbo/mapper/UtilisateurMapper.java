package com.kobbo.kobbo.mapper;

import com.kobbo.kobbo.dto.utilisateur.request.RegisterUtilisateurRequest;
import com.kobbo.kobbo.dto.utilisateur.request.UpdateUtilisateurRequest;
import com.kobbo.kobbo.dto.utilisateur.response.UtilisateurDto;
import com.kobbo.kobbo.dto.utilisateur.response.UtilisateurResponse;
import com.kobbo.kobbo.entity.Utilisateur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UtilisateurMapper {
    @Mapping(source = "role.id", target = "roleResponse.id")
    UtilisateurDto toDto(Utilisateur utilisateur);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    Utilisateur toEntity(RegisterUtilisateurRequest request);

    @Mapping(source = "role.id", target = "roleResponse.id")
    UtilisateurResponse toUtilisateurResponse(Utilisateur utilisateur);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "roleId", target = "role.id")
    void update(UpdateUtilisateurRequest request, @MappingTarget Utilisateur utilisateur);
}
