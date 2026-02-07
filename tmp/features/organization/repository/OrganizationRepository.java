package com.eagle.futbolapi.features.organization.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.enums.OrganizationType;
import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.organization.entity.Organization;

@Repository
public interface OrganizationRepository extends BaseRepository<Organization, Long> {

  Optional<Organization> findByName(String name);

  Optional<Organization> findByDisplayName(String displayName);

  Optional<Organization> findByAbbreviation(String abbreviation);

  Page<Organization> findByType(OrganizationType type, Pageable pageable);

  // Unique field methods: name + country + type
  @Query("SELECT o FROM Organization o WHERE o.name = :name AND o.country.id = :countryId AND o.type = :type")
  Optional<Organization> findByNameAndCountryIdAndType(
      @Param("name") String name,
      @Param("countryId") Long countryId,
      @Param("type") OrganizationType type);

  @Query("SELECT COUNT(o) > 0 FROM Organization o WHERE o.name = :name AND o.country.id = :countryId AND o.type = :type")
  boolean existsByNameAndCountryIdAndType(
      @Param("name") String name,
      @Param("countryId") Long countryId,
      @Param("type") OrganizationType type);

  @Query("SELECT COUNT(o) > 0 FROM Organization o WHERE o.name = :name AND o.country.id = :countryId AND o.type = :type AND o.id != :id")
  boolean existsByNameAndCountryIdAndTypeAndIdNot(
      @Param("name") String name,
      @Param("countryId") Long countryId,
      @Param("type") OrganizationType type,
      @Param("id") Long id);

  boolean existsByName(String name);

  boolean existsByDisplayName(String displayName);

  boolean existsByAbbreviation(String abbreviation);

  boolean existsByNameAndIdNot(String name, Long id);

  boolean existsByDisplayNameAndIdNot(String displayName, Long id);

  boolean existsByAbbreviationAndIdNot(String abbreviation, Long id);

}
