package com.eagle.futbolapi.features.season.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eagle.futbolapi.features.season.entity.Season;

public interface SeasonRepository extends JpaRepository<Season, Long> {

}
