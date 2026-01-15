package com.eagle.futbolapi.features.organization.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.organization.entity.Organization;
import com.eagle.futbolapi.features.organization.entity.OrganizationType;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    Optional<Organization> findByName(String name);

    Optional<Organization> findByDisplayName(String displayName);

    Optional<Organization> findByAbbreviation(String abbreviation);

    Page<Organization> findByType(OrganizationType type, Pageable pageable);

    boolean existsByName(String name);

    boolean existsByDisplayName(String displayName);

    boolean existsByAbbreviation(String abbreviation);

    boolean existsByNameAndIdNot(String name, Long id);

    boolean existsByDisplayNameAndIdNot(String displayName, Long id);

    boolean existsByAbbreviationAndIdNot(String abbreviation, Long id);

}
