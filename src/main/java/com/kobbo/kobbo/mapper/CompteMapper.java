package com.kobbo.kobbo.mapper;

import com.kobbo.kobbo.dto.comptes.response.CompteDto;
import com.kobbo.kobbo.dto.comptes.response.CompteResponse;
import com.kobbo.kobbo.entity.Compte;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompteMapper {
    CompteDto toDto(Compte compte);

    CompteResponse toCompteResponse(Compte compte);


}
