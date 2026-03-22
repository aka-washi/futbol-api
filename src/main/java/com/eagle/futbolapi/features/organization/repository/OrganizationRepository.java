package com.eagle.futbolapi.features.organization.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.organization.entity.Organization;

/**
 * Repository interface for Organization entity data access operations.
 */
@Repository
public interface OrganizationRepository extends BaseRepository<Organization, Long> {

  Optional<Organization> findByDisplayName(String displayName);

}
