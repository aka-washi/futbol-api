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
  @Mapping(target = "type", expression = "java(transitionTypeToString(organizationTransition.getTransitionType()))")
  OrganizationTransitionDto toDto(OrganizationTransition organizationTransition);

  @Mapping(target = "fromOrganization", ignore = true)
  @Mapping(target = "toOrganization", ignore = true)
  @Mapping(target = "type", expression = "java(stringToTransitionType(organizationTransitionDto.getType()))")
  OrganizationTransition toEntity(OrganizationTransitionDto organizationTransitionDto);

  default String transitionTypeToString(TransitionType type) {
    return type != null ? type.getLabel() : null;
  }

  default TransitionType stringToTransitionType(String type) {
    return TransitionType.fromLabel(type);
  }

}
