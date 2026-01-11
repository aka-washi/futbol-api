package com.eagle.futbolapi.features.competition.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.competition.entity.Competition;
import com.eagle.futbolapi.features.competition.entity.CompetitionType;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {

    Optional<Competition> findByName(String name);

    Optional<Competition> findByDisplayName(String displayName);

    List<Competition> findByType(CompetitionType type);

    List<Competition> findBySeasonId(Long seasonId);

    List<Competition> findByActive(Boolean active);

    @Query("SELECT c FROM Competition c WHERE c.season.id = :seasonId AND c.type = :type")
    List<Competition> findBySeasonIdAndType(@Param("seasonId") Long seasonId, @Param("type") CompetitionType type);

    @Query("SELECT c FROM Competition c WHERE c.season.id = :seasonId AND c.active = :active")
    List<Competition> findBySeasonIdAndActive(@Param("seasonId") Long seasonId, @Param("active") Boolean active);

    boolean existsByName(String name);

    boolean existsByDisplayName(String displayName);

    boolean existsBySeasonIdAndName(Long seasonId, String name);

    boolean existsBySeasonIdAndDisplayName(Long seasonId, String displayName);
}
