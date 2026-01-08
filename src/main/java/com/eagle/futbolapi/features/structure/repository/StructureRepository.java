package com.eagle.futbolapi.features.structure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.pointsystem.entity.PointSystem;
import com.eagle.futbolapi.features.structure.entity.Structure;
import com.eagle.futbolapi.features.structure.entity.StructureType;

@Repository
public interface StructureRepository extends JpaRepository<Structure, Long> {

    Optional<Structure> findByName(String name);

    Optional<Structure> findByDisplayName(String displayName);

    List<Structure> findByType(StructureType type);

    List<Structure> findByPointSystem(PointSystem pointSystem);

    List<Structure> findByPointSystemId(Long pointSystemId);

    List<Structure> findByNumberOfTeams(Integer numberOfTeams);

    @Query("SELECT s FROM Structure s WHERE s.hasHomeAndAway = :hasHomeAndAway")
    List<Structure> findByHasHomeAndAway(@Param("hasHomeAndAway") Boolean hasHomeAndAway);

    @Query("SELECT s FROM Structure s WHERE s.numberOfTeams >= :minTeams AND s.numberOfTeams <= :maxTeams")
    List<Structure> findByTeamsRange(@Param("minTeams") Integer minTeams, @Param("maxTeams") Integer maxTeams);

    List<Structure> findByTypeAndNumberOfTeams(StructureType type, Integer numberOfTeams);

}
