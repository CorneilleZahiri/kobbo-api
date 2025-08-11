package com.kobbo.kobbo.mapper;

import com.kobbo.kobbo.dto.comptes.response.CompteDto;
import com.kobbo.kobbo.entity.Compte;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompteMapper {
    @Mapping(source = "utilisateur", target = "utilisateurDto")
    @Mapping(source = "role", target = "roleDto")
    @Mapping(source = "role.societe", target = "roleDto.societeDto")
    CompteDto toDto(Compte compte);
}
