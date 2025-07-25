package com.kobbo.kobbo.mapper;

import com.kobbo.kobbo.dto.operation.request.RegisterOperationRequest;
import com.kobbo.kobbo.dto.operation.response.OperationDto;
import com.kobbo.kobbo.entity.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OperationMapper {

    @Mapping(source = "tiers.id", target = "tiersResponse.id")
    @Mapping(source = "nature.id", target = "natureResponse.id")
    @Mapping(source = "utilisateur.id", target = "utilisateurWithLibelleRoleResponse.id")
    @Mapping(source = "utilisateur.role.libelle", target = "utilisateurWithLibelleRoleResponse.libelleRole")
    OperationDto toDto(Operation operation);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "numeroFormater", ignore = true)
    @Mapping(source = "tiersId", target = "tiers.id")
    @Mapping(source = "natureId", target = "nature.id")
    @Mapping(source = "utilisateurId", target = "utilisateur.id")
    Operation registerToEntity(RegisterOperationRequest request);

}
