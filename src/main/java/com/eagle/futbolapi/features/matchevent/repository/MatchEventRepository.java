package com.eagle.futbolapi.features.matchevent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.matchevent.entity.MatchEvent;

@Repository
public interface MatchEventRepository extends JpaRepository<MatchEvent, Long> {

}
