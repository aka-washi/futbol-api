package com.eagle.futbolapi.features.competition.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.enums.CompetitionType;
import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.competition.entity.Competition;

@Repository
public interface CompetitionRepository extends BaseRepository<Competition, Long> {

        Optional<Competition> findByName(String name);

        Optional<Competition> findByDisplayName(String displayName);

        Optional<Competition> findBySeasonIdAndActive(Long seasonId, Boolean active);

        @Query("SELECT c FROM Competition c WHERE c.type = :type " +
                        "AND c.startDate <= :date AND c.endDate >= :date")
        Optional<Competition> findByTypeAndDate(CompetitionType type, LocalDate date);

        // Unique field methods: season + type
        @Query("SELECT c FROM Competition c WHERE c.season.id = :seasonId AND c.type = :type")
        Optional<Competition> findBySeasonIdAndType(
                        @Param("seasonId") Long seasonId,
                        @Param("type") CompetitionType type);

        @Query("SELECT COUNT(c) > 0 FROM Competition c WHERE c.season.id = :seasonId AND c.type = :type")
        boolean existsBySeasonIdAndType(
                        @Param("seasonId") Long seasonId,
                        @Param("type") CompetitionType type);

        @Query("SELECT COUNT(c) > 0 FROM Competition c WHERE c.season.id = :seasonId AND c.type = :type AND c.id != :id")
        boolean existsBySeasonIdAndTypeAndIdNot(
                        @Param("seasonId") Long seasonId,
                        @Param("type") CompetitionType type,
                        @Param("id") Long id);

        @Query("SELECT c FROM Competition c WHERE c.active = true")
        Page<Competition> findActiveCompetitions(Pageable pageable);

        @Query("SELECT c FROM Competition c WHERE c.season.id = :seasonId")
        Page<Competition> findBySeasonId(Long seasonId, Pageable pageable);

        @Query("SELECT c FROM Competition c WHERE c.startDate >= :starDate AND c.endDate <= :endDate")
        Page<Competition> findByDateRange(LocalDate starDate, LocalDate endDate, Pageable pageable);
}
