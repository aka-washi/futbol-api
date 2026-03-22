package com.eagle.futbolapi.features.tournamentSeason.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.tournamentSeason.entity.TournamentSeason;

/**
 * Repository interface for TournamentSeason entity data access operations.
 */
@Repository
public interface TournamentSeasonRepository extends BaseRepository<TournamentSeason, Long> {

  Optional<TournamentSeason> findByDisplayName(String displayName);

}
