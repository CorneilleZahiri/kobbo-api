package com.kobbo.kobbo.mapper;

import com.kobbo.kobbo.dto.societe.RegisterSocieteRequest;
import com.kobbo.kobbo.dto.societe.SocieteDto;
import com.kobbo.kobbo.entity.Societe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SocieteMapper {
    SocieteDto toDto(Societe societe);

    @Mapping(target = "id", ignore = true)
    Societe toEntity(RegisterSocieteRequest request);

}
