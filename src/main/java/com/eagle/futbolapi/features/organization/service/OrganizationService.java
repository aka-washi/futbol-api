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
import com.eagle.futbolapi.features.shared.exception.DuplicateResourceException;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.shared.service.BaseCrudService;

@Service
@Transactional
public class OrganizationService extends BaseCrudService<Organization, Long> {

    private final OrganizationRepository organizationRepository;

    protected OrganizationService(OrganizationRepository organizationRepository) {
        super(organizationRepository);
        this.organizationRepository = organizationRepository;
    }

    public Page<Organization> getAllOrganizations(Pageable pageable) {
        if (pageable == null) {
            pageable = Pageable.unpaged();
        }
        return organizationRepository.findAll(pageable);
    }

    public Optional<Organization> getOrganizationById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Organization ID cannot be null");
        }
        return organizationRepository.findById(id);
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

    public Organization createOrganization(@NotNull Organization organization) {
        Objects.requireNonNull(organization, "Organization cannot be null");

        // Check for duplicate name
        if (organization.getName() != null && organizationRepository.existsByName(organization.getName())) {
            throw new DuplicateResourceException(
                    "Organization with name '" + organization.getName() + "' already exists");
        }

        // Check for duplicate display name
        if (organization.getDisplayName() != null
                && organizationRepository.existsByDisplayName(organization.getDisplayName())) {
            throw new DuplicateResourceException(
                    "Organization with display name '" + organization.getDisplayName() + "' already exists");
        }

        // Check for duplicate abbreviation if provided
        if (organization.getAbbreviation() != null && !organization.getAbbreviation().trim().isEmpty()
                && organizationRepository.existsByAbbreviation(organization.getAbbreviation())) {
            throw new DuplicateResourceException(
                    "Organization with abbreviation '" + organization.getAbbreviation() + "' already exists");
        }

        return Objects.requireNonNull(
                organizationRepository.save(organization),
                "Organization save operation returned null - this should never happen");
    }

    public Organization updateOrganization(@NotNull Organization organization) {
        Objects.requireNonNull(organization, "Organization cannot be null");
        Objects.requireNonNull(organization.getId(), "Organization ID cannot be null for update operation");

        Organization existingOrganization = organizationRepository.findById(organization.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Organization", "id", organization.getId()));

        // Check for duplicate name only if name is changing
        if (organization.getName() != null && !organization.getName().equals(existingOrganization.getName())) {
            organizationRepository.findByName(organization.getName())
                    .ifPresent(org -> {
                        if (!org.getId().equals(organization.getId())) {
                            throw new DuplicateResourceException(
                                    "Organization with name '" + organization.getName() + "' already exists");
                        }
                    });
        }

        // Check for duplicate display name only if display name is changing
        if (organization.getDisplayName() != null
                && !organization.getDisplayName().equals(existingOrganization.getDisplayName())) {
            organizationRepository.findByDisplayName(organization.getDisplayName())
                    .ifPresent(org -> {
                        if (!org.getId().equals(organization.getId())) {
                            throw new DuplicateResourceException("Organization with display name '"
                                    + organization.getDisplayName() + "' already exists");
                        }
                    });
        }

        // Check for duplicate abbreviation only if abbreviation is changing
        if (organization.getAbbreviation() != null && !organization.getAbbreviation().trim().isEmpty()
                && !organization.getAbbreviation().equals(existingOrganization.getAbbreviation())) {
            organizationRepository.findByAbbreviation(organization.getAbbreviation())
                    .ifPresent(org -> {
                        if (!org.getId().equals(organization.getId())) {
                            throw new DuplicateResourceException("Organization with abbreviation '"
                                    + organization.getAbbreviation() + "' already exists");
                        }
                    });
        }

        existingOrganization.setName(organization.getName());
        existingOrganization.setDisplayName(organization.getDisplayName());
        existingOrganization.setAbbreviation(organization.getAbbreviation());
        existingOrganization.setFounded(organization.getFounded());
        existingOrganization.setParentOrganization(organization.getParentOrganization());
        existingOrganization.setCountry(organization.getCountry());
        existingOrganization.setHeadquarters(organization.getHeadquarters());
        existingOrganization.setLogo(organization.getLogo());
        existingOrganization.setWebsite(organization.getWebsite());
        existingOrganization.setDescription(organization.getDescription());
        existingOrganization.setType(organization.getType());

        return Objects.requireNonNull(
                organizationRepository.save(existingOrganization),
                "Organization update operation returned null - this should never happen");
    }

    public void deleteOrganization(@NotNull Long id) {
        Objects.requireNonNull(id, "Organization ID cannot be null");

        if (!organizationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Organization", "id", id);
        }
        organizationRepository.deleteById(id);
    }

    public boolean existsById(@NotNull Long id) {
        Objects.requireNonNull(id, "Organization ID cannot be null");
        return organizationRepository.existsById(id);
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
        Objects.requireNonNull(organization.getId(), "Organization ID cannot be null for update operation");

        Organization existingOrganization = organizationRepository.findById(organization.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Organization", "id", organization.getId()));

        // Check for duplicate name only if name is changing
        if (organization.getName() != null && !organization.getName().equals(existingOrganization.getName())) {
            return true;
        }

        // Check for duplicate display name only if display name is changing
        if (organization.getDisplayName() != null
                && !organization.getDisplayName().equals(existingOrganization.getDisplayName())) {
            return true;
        }

        // Check for duplicate abbreviation only if abbreviation is changing
        return organization.getAbbreviation() != null
                && !organization.getAbbreviation().trim().isEmpty()
                && !organization.getAbbreviation().equals(existingOrganization.getAbbreviation());
    }

}
