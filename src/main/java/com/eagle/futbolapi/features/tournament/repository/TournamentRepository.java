package com.eagle.futbolapi.features.tournament.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eagle.futbolapi.features.tournament.entity.Tournament;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {

}
