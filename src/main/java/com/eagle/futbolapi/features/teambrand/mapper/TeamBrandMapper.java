package com.eagle.futbolapi.features.teambrand.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.teambrand.dto.TeamBrandDto;
import com.eagle.futbolapi.features.teambrand.entity.TeamBrand;

/**
 * MapStruct mapper for TeamBrand entity and DTO conversion.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TeamBrandMapper extends BaseMapper<TeamBrand, TeamBrandDto> {

  @Mapping(target = "teamId", source = "team.id")
  @Mapping(target = "teamDisplayName", source = "team.displayName")
  TeamBrandDto toDto(TeamBrand teamBrand);

  @Mapping(target = "team", ignore = true)
  TeamBrand toEntity(TeamBrandDto teamBrandDto);

}
