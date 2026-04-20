package com.eagle.futbolapi.features.organizationtransition.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.enums.TransitionType;
import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.organizationtransition.dto.OrganizationTransitionDto;
import com.eagle.futbolapi.features.organizationtransition.entity.OrganizationTransition;

/**
 * MapStruct mapper for OrganizationTransition entity and DTO conversion.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrganizationTransitionMapper extends BaseMapper<OrganizationTransition, OrganizationTransitionDto> {


  @Mapping(target = "fromOrganizationId", source = "fromOrganization.id")
  @Mapping(target = "fromOrganizationDisplayName", source = "fromOrganization.displayName")
  @Mapping(target = "toOrganizationId", source = "toOrganization.id")
  @Mapping(target = "toOrganizationDisplayName", source = "toOrganization.displayName")
  @Mapping(target = "effectiveSeasonId", source = "effectiveSeason.id")
  @Mapping(target = "effectiveSeasonDisplayName", source = "effectiveSeason.displayName")
  @Mapping(target = "transitionType", expression = "java(transitionTypeToString(organizationTransition.getTransitionType()))")
  OrganizationTransitionDto toDto(OrganizationTransition organizationTransition);

  @Mapping(target = "fromOrganization", ignore = true)
  @Mapping(target = "toOrganization", ignore = true)
  @Mapping(target = "effectiveSeason", ignore = true)
  @Mapping(target = "transitionType", expression = "java(stringToTransitionType(organizationTransitionDto.getTransitionType()))")
  OrganizationTransition toEntity(OrganizationTransitionDto organizationTransitionDto);

  default String transitionTypeToString(TransitionType transitionType) {
    return transitionType != null ? transitionType.getLabel() : null;
  }

  default TransitionType stringToTransitionType(String transitionType) {
    return TransitionType.fromLabel(transitionType);
  }
}
