package com.kobbo.kobbo.mapper;

import com.kobbo.kobbo.dto.role.request.RegisterRoleRequest;
import com.kobbo.kobbo.dto.role.response.RoleDto;
import com.kobbo.kobbo.dto.role.response.RoleResponse;
import com.kobbo.kobbo.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto toDto(Role role);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "societe", ignore = true)
    Role toEntity(RegisterRoleRequest roleRequest);

    RoleResponse toRoleResponse(Role role);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "societe", ignore = true)
    void update(RegisterRoleRequest roleRequest, @MappingTarget Role role);
}
