package com.kobbo.kobbo.mapper;

import com.kobbo.kobbo.dto.societe.request.RegisterSocieteRequest;
import com.kobbo.kobbo.dto.societe.response.SocieteDto;
import com.kobbo.kobbo.entity.Societe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SocieteMapper {
    SocieteDto toDto(Societe societe);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "natures", ignore = true)
    Societe toEntity(RegisterSocieteRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "natures", ignore = true)
    void update(RegisterSocieteRequest request, @MappingTarget Societe societe);
}
