package com.kobbo.kobbo.mapper;

import com.kobbo.kobbo.dto.responsable.request.RegisterResponsableRequest;
import com.kobbo.kobbo.dto.responsable.response.ResponsableDto;
import com.kobbo.kobbo.dto.responsable.response.ResponsableResponse;
import com.kobbo.kobbo.entity.Responsable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ResponsableMapper {
    @Mapping(source = "societe.id", target = "societeDto.id")
    ResponsableDto toDto(Responsable responsable);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "societe", ignore = true)
    Responsable toEntity(RegisterResponsableRequest responsableRequest);

    ResponsableResponse toResponsableResponse(Responsable responsable);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "societe", ignore = true)
    void update(RegisterResponsableRequest responsableRequest, @MappingTarget Responsable responsable);
}
