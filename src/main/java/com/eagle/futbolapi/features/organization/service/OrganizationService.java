package com.eagle.futbolapi.features.organization.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.organization.entity.Organization;
import com.eagle.futbolapi.features.organization.entity.OrganizationType;
import com.eagle.futbolapi.features.organization.repository.OrganizationRepository;
import com.eagle.futbolapi.features.shared.service.BaseCrudService;

@Service
@Transactional
public class OrganizationService extends BaseCrudService<Organization, Long> {

    private final OrganizationRepository organizationRepository;

    protected OrganizationService(OrganizationRepository organizationRepository) {
        super(organizationRepository);
        this.organizationRepository = organizationRepository;
    }

    public Optional<Organization> getOrganizationByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Organization name cannot be null or empty");
        }
        return organizationRepository.findByName(name);
    }

    public Optional<Organization> getOrganizationByDisplayName(String displayName) {
        if (displayName == null || displayName.trim().isEmpty()) {
            throw new IllegalArgumentException("Organization display name cannot be null or empty");
        }
        return organizationRepository.findByDisplayName(displayName);
    }

    public Optional<Organization> getOrganizationByAbbreviation(String abbreviation) {
        if (abbreviation == null || abbreviation.trim().isEmpty()) {
            throw new IllegalArgumentException("Organization abbreviation cannot be null or empty");
        }
        return organizationRepository.findByAbbreviation(abbreviation);
    }

    public Page<Organization> getOrganizationsByType(OrganizationType type, Pageable pageable) {
        if (pageable == null) {
            pageable = Pageable.unpaged();
        }
        if (type == null) {
            throw new IllegalArgumentException("Organization type cannot be null");
        }
        return organizationRepository.findByType(type, pageable);
    }

    public Page<Organization> getOrganizationsByCountryId(Long countryId, Pageable pageable) {
        if (pageable == null) {
            pageable = Pageable.unpaged();
        }
        if (countryId == null) {
            throw new IllegalArgumentException("Country ID cannot be null");
        }
        return organizationRepository.findByCountryId(countryId, pageable);
    }

    public Page<Organization> getOrganizationsByParentOrganizationId(Long parentId, Pageable pageable) {
        if (pageable == null) {
            pageable = Pageable.unpaged();
        }
        if (parentId == null) {
            throw new IllegalArgumentException("Parent Organization ID cannot be null");
        }
        return organizationRepository.findByParentOrganizationId(parentId, pageable);
    }

    public Page<Organization> searchOrganizationsByName(String searchTerm, Pageable pageable) {
        if (pageable == null) {
            pageable = Pageable.unpaged();
        }
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            throw new IllegalArgumentException("Search term cannot be null or empty");
        }
        return organizationRepository.findByNameContainingIgnoreCase(searchTerm, pageable);
    }

    @Override
    protected boolean isDuplicate(@NotNull Organization organization) {
        Objects.requireNonNull(organization, "Organization cannot be null");

        // Check for duplicate name
        if (organization.getName() != null && organizationRepository.existsByName(organization.getName())) {
            return true;
        }

        // Check for duplicate display name
        if (organization.getDisplayName() != null
                && organizationRepository.existsByDisplayName(organization.getDisplayName())) {
            return true;
        }

        // Check for duplicate abbreviation if provided
        return organization.getAbbreviation() != null && !organization.getAbbreviation().trim().isEmpty()
                && organizationRepository.existsByAbbreviation(organization.getAbbreviation());

    }

    @Override
    protected boolean isDuplicate(Long id, @NotNull Organization organization) {
        Objects.requireNonNull(organization, "Organization cannot be null");
        Objects.requireNonNull(id, "ID cannot be null for update operation");

        // Check for duplicate name only if name is changing
        if (organization.getName() != null) {
            Optional<Organization> existingByName = organizationRepository.findByName(organization.getName());
            if (existingByName.isPresent() && !existingByName.get().getId().equals(id)) {
                return true;
            }
        }

        // Check for duplicate display name only if display name is changing
        if (organization.getDisplayName() != null) {
            Optional<Organization> existingByDisplayName = organizationRepository.findByDisplayName(organization.getDisplayName());
            if (existingByDisplayName.isPresent() && !existingByDisplayName.get().getId().equals(id)) {
                return true;
            }
        }

        // Check for duplicate abbreviation only if abbreviation is changing
        if (organization.getAbbreviation() != null && !organization.getAbbreviation().trim().isEmpty()) {
            Optional<Organization> existingByAbbreviation = organizationRepository.findByAbbreviation(organization.getAbbreviation());
            if (existingByAbbreviation.isPresent() && !existingByAbbreviation.get().getId().equals(id)) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected boolean entitiesEqual(Organization existing, Organization updated) {
        if (existing == null || updated == null) {
            return false;
        }

        return Objects.equals(existing.getName(), updated.getName()) &&
               Objects.equals(existing.getDisplayName(), updated.getDisplayName()) &&
               Objects.equals(existing.getAbbreviation(), updated.getAbbreviation()) &&
               Objects.equals(existing.getFounded(), updated.getFounded()) &&
               Objects.equals(existing.getCountry(), updated.getCountry()) &&
               Objects.equals(existing.getParentOrganization(), updated.getParentOrganization()) &&
               Objects.equals(existing.getHeadquarters(), updated.getHeadquarters()) &&
               Objects.equals(existing.getLogo(), updated.getLogo()) &&
               Objects.equals(existing.getWebsite(), updated.getWebsite()) &&
               Objects.equals(existing.getDescription(), updated.getDescription()) &&
               Objects.equals(existing.getType(), updated.getType());
    }

}
