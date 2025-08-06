//package com.kobbo.kobbo.mapper;
//
//import com.kobbo.kobbo.dto.tiers.request.RegisterTiersRequest;
//import com.kobbo.kobbo.dto.tiers.response.TiersDto;
//import com.kobbo.kobbo.dto.tiers.response.TiersResponse;
//import com.kobbo.kobbo.entity.Tiers;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.MappingTarget;
//
//@Mapper(componentModel = "spring")
//public interface TiersMapper {
//    TiersDto toDto(Tiers tiers);
//
//    @Mapping(target = "id", ignore = true)
//    Tiers toEntity(RegisterTiersRequest tiersRequest);
//
//    TiersResponse toTiersResponse(Tiers tiers);
//
//    @Mapping(target = "id", ignore = true)
//    void update(RegisterTiersRequest tiersRequest, @MappingTarget Tiers tiers);
//}
