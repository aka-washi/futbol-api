package com.eagle.futbolapi.features.organization.mapper;

import org.springframework.stereotype.Component;

import com.eagle.futbolapi.features.country.repository.CountryRepository;
import com.eagle.futbolapi.features.organization.dto.OrganizationDTO;
import com.eagle.futbolapi.features.organization.entity.Organization;
import com.eagle.futbolapi.features.organization.entity.OrganizationType;
import com.eagle.futbolapi.features.organization.repository.OrganizationRepository;

@Component
public class OrganizationMapper {

    private final CountryRepository countryRepository;
    private final OrganizationRepository organizationRepository;

    public OrganizationMapper(CountryRepository countryRepository, OrganizationRepository organizationRepository) {
        this.countryRepository = countryRepository;
        this.organizationRepository = organizationRepository;
    }

    public OrganizationDTO toOrganizationDTO(Organization organization) {
        if (organization == null) {
            return null;
        }

        return OrganizationDTO.builder()
                .id(organization.getId())
                .name(organization.getName())
                .displayName(organization.getDisplayName())
                .abbreviation(organization.getAbbreviation())
                .countryId(organization.getCountry() != null ? organization.getCountry().getId() : null)
                .countryDisplayName(
                        organization.getCountry() != null ? organization.getCountry().getDisplayName() : null)
                .parentOrganizationId(
                        organization.getParentOrganization() != null ? organization.getParentOrganization().getId()
                                : null)
                .parentOrganizationDisplayName(organization.getParentOrganization() != null
                        ? organization.getParentOrganization().getDisplayName()
                        : null)
                .founded(organization.getFounded())
                .headquarters(organization.getHeadquarters())
                .logo(organization.getLogo())
                .website(organization.getWebsite())
                .description(organization.getDescription())
                .type(organization.getType().name())
                .createdAt(organization.getCreatedAt())
                .createdBy(organization.getCreatedBy())
                .updatedAt(organization.getUpdatedAt())
                .updatedBy(organization.getUpdatedBy())
                .build();
    }

    public Organization toOrganizationEntity(OrganizationDTO organizationDTO) {
        if (organizationDTO == null) {
            return null;
        }

        var builder = Organization.builder()
                .id(organizationDTO.getId())
                .name(organizationDTO.getName())
                .displayName(organizationDTO.getDisplayName())
                .abbreviation(organizationDTO.getAbbreviation())
                .founded(organizationDTO.getFounded())
                .headquarters(organizationDTO.getHeadquarters())
                .logo(organizationDTO.getLogo())
                .website(organizationDTO.getWebsite())
                .description(organizationDTO.getDescription())
                .createdAt(organizationDTO.getCreatedAt())
                .createdBy(organizationDTO.getCreatedBy())
                .updatedAt(organizationDTO.getUpdatedAt())
                .updatedBy(organizationDTO.getUpdatedBy());

        if (organizationDTO.getType() != null) {
            try {
                builder.type(OrganizationType.valueOf(organizationDTO.getType().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid organization type: " + organizationDTO.getType());
            }
        }
        // Handle country lookup - prioritize ID over displayName
        if (organizationDTO.getCountryId() != null) {
            var country = countryRepository.findById(organizationDTO.getCountryId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Country not found with id: " + organizationDTO.getCountryId()));
            builder.country(country);
        } else if (organizationDTO.getCountryDisplayName() != null && !organizationDTO.getCountryDisplayName().trim().isEmpty()) {
            var country = countryRepository.findByDisplayName(organizationDTO.getCountryDisplayName())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Country not found with display name: " + organizationDTO.getCountryDisplayName()));
            builder.country(country);
        }
        if (organizationDTO.getParentOrganizationId() != null) {
            var parentOrganization = organizationRepository.findById(organizationDTO.getParentOrganizationId())
                    .orElse(null);
            builder.parentOrganization(parentOrganization);
        }

        return builder.build();
    }
}
