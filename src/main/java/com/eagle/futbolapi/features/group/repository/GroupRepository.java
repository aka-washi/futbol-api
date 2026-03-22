package com.eagle.futbolapi.features.group.repository;

import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.group.entity.Group;

/**
 * Repository interface for Group entity.
 */
@Repository
public interface GroupRepository extends BaseRepository<Group, Long> {
}
