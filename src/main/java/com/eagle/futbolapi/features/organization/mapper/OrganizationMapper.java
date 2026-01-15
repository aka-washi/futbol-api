package com.eagle.futbolapi.features.organization.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.eagle.futbolapi.features.organization.dto.OrganizationDTO;
import com.eagle.futbolapi.features.organization.entity.Organization;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrganizationMapper {

    OrganizationDTO toOrganizationDTO(Organization organization);

    Organization toOrganization(OrganizationDTO organizationDTO);
}
