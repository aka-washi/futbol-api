package com.eagle.futbolapi.features.organization.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.organization.entity.Organization;
import com.eagle.futbolapi.features.organization.entity.OrganizationType;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    Optional<Organization> findByName(String name);

    Optional<Organization> findByDisplayName(String displayName);

    Optional<Organization> findByAbbreviation(String abbreviation);

    Page<Organization> findByType(OrganizationType type, Pageable pageable);

    @Query("SELECT o FROM Organization o WHERE o.country.id = :countryId")
    Page<Organization> findByCountryId(Long countryId, Pageable pageable);

    @Query("SELECT o FROM Organization o WHERE o.parentOrganization.id = :parentId")
    Page<Organization> findByParentOrganizationId(@Param("parentId") Long parentId, Pageable pageable);

    @Query("SELECT o FROM Organization o WHERE o.name ILIKE %:searchTerm% OR o.displayName ILIKE %:searchTerm%")
    Page<Organization> findByNameContainingIgnoreCase(@Param("searchTerm") String searchTerm, Pageable pageable);

    boolean existsByName(String name);

    boolean existsByDisplayName(String displayName);

    boolean existsByAbbreviation(String abbreviation);

}
