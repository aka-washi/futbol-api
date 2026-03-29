package com.eagle.futbolapi.features.organizationtransition.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.organizationtransition.entity.OrganizationTransition;

/**
 * Repository for OrganizationTransition entity data access.
 */
@Repository
public interface OrganizationTransitionRepository extends BaseRepository<OrganizationTransition, Long> {

  Optional<OrganizationTransition> findByDisplayName(String displayName);

}
