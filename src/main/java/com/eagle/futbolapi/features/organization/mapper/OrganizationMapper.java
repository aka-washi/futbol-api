package com.eagle.futbolapi.features.organization.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.enums.OrganizationType;
import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.organization.dto.OrganizationDto;
import com.eagle.futbolapi.features.organization.entity.Organization;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrganizationMapper extends BaseMapper<Organization, OrganizationDto> {

  @Mapping(target = "countryId", source = "country.id")
  @Mapping(target = "countryDisplayName", source = "country.displayName")
  @Mapping(target = "parentOrganizationId", source = "parentOrganization.id")
  @Mapping(target = "parentOrganizationDisplayName", source = "parentOrganization.displayName")
  @Mapping(target = "type", expression = "java(organizationTypeToString(organization.getType()))")
  OrganizationDto toDto(Organization organization);

  @Mapping(target = "country", ignore = true)
  @Mapping(target = "parentOrganization", ignore = true)
  @Mapping(target = "type", expression = "java(stringToOrganizationType(organizationDto.getType()))")
  Organization toEntity(OrganizationDto organizationDto);

  default String organizationTypeToString(OrganizationType type) {
    return type != null ? type.getLabel() : null;
  }

  default OrganizationType stringToOrganizationType(String type) {
    if (type == null)
      return null;
    for (OrganizationType orgType : OrganizationType.values()) {
      if (orgType.getLabel().equals(type)) {
        return orgType;
      }
    }
    throw new IllegalArgumentException("Invalid organization type: " + type);
  }
}
