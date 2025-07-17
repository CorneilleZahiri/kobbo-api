package com.kobbo.kobbo.mapper;

import com.kobbo.kobbo.dto.nature.NatureDto;
import com.kobbo.kobbo.dto.nature.RegisterNatureRequest;
import com.kobbo.kobbo.entity.Nature;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NatureMapper {
    @Mapping(source = "societe.id", target = "societeDto.id")
    NatureDto toDto(Nature nature);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "societe", ignore = true)
    Nature toEntity(RegisterNatureRequest natureRequest);
}
