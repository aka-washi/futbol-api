package com.eagle.futbolapi.features.organization.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.organization.dto.OrganizationDTO;
import com.eagle.futbolapi.features.organization.entity.Organization;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrganizationMapper extends BaseMapper<Organization, OrganizationDTO> {

  @Mapping(target = "countryId", source = "country.id")
  @Mapping(target = "countryDisplayName", source = "country.displayName")
  @Mapping(target = "parentOrganizationId", source = "parentOrganization.id")
  @Mapping(target = "parentOrganizationDisplayName", source = "parentOrganization.displayName")
  OrganizationDTO toDTO(Organization organization);

  @Mapping(target = "country", ignore = true)
  @Mapping(target = "parentOrganization", ignore = true)
  Organization toEntity(OrganizationDTO organizationDTO);

}
