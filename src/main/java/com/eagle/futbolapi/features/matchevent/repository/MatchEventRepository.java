package com.eagle.futbolapi.features.matchevent.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eagle.futbolapi.features.matchevent.entity.MatchEvent;

public interface MatchEventRepository extends JpaRepository<MatchEvent, Long> {

}
