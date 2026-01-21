package com.eagle.futbolapi.features.matchday.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.matchday.entity.Matchday;

@Repository
public interface MatchdayRepository extends JpaRepository<Matchday, Long> {

}
