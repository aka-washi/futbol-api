package com.eagle.futbolapi.features.tournament.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.tournament.entity.Tournament;

@Repository
public interface TournamentRepository extends BaseRepository<Tournament, Long> {

  Optional<Tournament> findByDisplayName(String displayName);

}
