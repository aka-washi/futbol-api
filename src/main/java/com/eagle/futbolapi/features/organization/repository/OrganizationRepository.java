package com.eagle.futbolapi.features.organization.repository;

import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.organization.entity.Organization;

/**
 * Repository interface for Organization entity data access operations.
 * Extends BaseRepository to inherit standard CRUD operations and custom query
 * methods.
 *
 * <p>
 * This repository is automatically implemented by Spring Data JPA at runtime.
 * Additional custom query methods can be added here if needed beyond the base
 * functionality.
 *
 * @see Organization
 * @see BaseRepository
 */
@Repository
public interface OrganizationRepository extends BaseRepository<Organization, Long> {

}
