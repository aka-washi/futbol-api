package com.eagle.futbolapi.features.team.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.enums.Gender;
import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.team.entity.Team;

/**
 * Repository for Team entity data access.
 */
@Repository
public interface TeamRepository extends BaseRepository<Team, Long> {

  Optional<Team> findByDisplayName(String displayName);

  Optional<Team> findByDisplayNameAndGender(String displayName, Gender gender);

}
