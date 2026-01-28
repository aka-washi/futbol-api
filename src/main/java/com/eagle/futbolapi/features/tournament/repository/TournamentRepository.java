package com.eagle.futbolapi.features.tournament.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.enums.TournamentType;
import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.tournament.entity.Tournament;

@Repository
public interface TournamentRepository extends BaseRepository<Tournament, Long> {

  Optional<Tournament> findByName(String name);

  Optional<Tournament> findByDisplayName(String displayName);

  // Unique field methods: name + organization
  @Query("SELECT t FROM Tournament t WHERE t.name = :name AND t.organization.id = :organizationId")
  Optional<Tournament> findByNameAndOrganizationId(
      @Param("name") String name,
      @Param("organizationId") Long organizationId);

  @Query("SELECT COUNT(t) > 0 FROM Tournament t WHERE t.name = :name AND t.organization.id = :organizationId")
  boolean existsByNameAndOrganizationId(
      @Param("name") String name,
      @Param("organizationId") Long organizationId);

  @Query("SELECT COUNT(t) > 0 FROM Tournament t WHERE t.name = :name AND t.organization.id = :organizationId AND t.id != :id")
  boolean existsByNameAndOrganizationIdAndIdNot(
      @Param("name") String name,
      @Param("organizationId") Long organizationId,
      @Param("id") Long id);

  Page<Tournament> findByOrganizationIdAndActive(Long organizationId, Boolean active, Pageable pageable);

  Page<Tournament> findByTypeAndActive(TournamentType type, Boolean active, Pageable pageable);

  Page<Tournament> findByOrganizationIdAndTypeAndActive(Long organizationId, TournamentType type, Boolean active,
      Pageable pageable);

  boolean existsByName(String name);

  boolean existsByDisplayName(String displayName);

  boolean existsByNameAndIdNot(String name, Long id);

  boolean existsByDisplayNameAndIdNot(String displayName, Long id);

}
