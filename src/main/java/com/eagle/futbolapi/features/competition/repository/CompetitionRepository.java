package com.eagle.futbolapi.features.competition.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.enums.CompetitionType;
import com.eagle.futbolapi.features.competition.entity.Competition;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {
}
