package com.eagle.futbolapi.features.tournament.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.enums.AgeCategory;
import com.eagle.futbolapi.features.base.enums.TournamentType;
import com.eagle.futbolapi.features.organization.entity.Organization;
import com.eagle.futbolapi.features.tournament.entity.Tournament;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {

  Optional<Tournament> findByName(String name);

  Optional<Tournament> findByDisplayName(String displayName);

  @Query("SELECT t FROM Tournament t WHERE t.organization = :organization " +
      "AND t.type = :type " +
      "AND t.ageCategory = :ageCategory " +
      "AND t.level = :level")
  Optional<Tournament> findByUniqueValues(
      @Param("organization") Organization organization,
      @Param("type") TournamentType type,
      @Param("ageCategory") String ageCategory,
      @Param("level") Integer level);

  Page<Tournament> findByOrganizationAndActive(Organization organization, Boolean active, Pageable pageable);

  Page<Tournament> findByTypeAndActive(TournamentType type, Boolean active, Pageable pageable);

  Page<Tournament> findByOrganizationAndTypeAndActive(Organization organization, TournamentType type, Boolean active,
      Pageable pageable);

  Page<Tournament> findByAgeCategoryAndOrganizationAndActive(String ageCategory, Organization organization,
      Boolean active, Pageable pageable);

  boolean existsByName(String name);

  boolean existsByDisplayName(String displayName);

  boolean existsByNameAndIdNot(String name, Long id);

  boolean existsByDisplayNameAndIdNot(String displayName, Long id);

  @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Tournament t WHERE t.organization = :organization "
      +
      "AND t.type = :type " +
      "AND t.ageCategory = :ageCategory " +
      "AND t.level = :level")
  boolean existsByUniqueValues(
      @Param("organization") Organization organization,
      @Param("type") TournamentType type,
      @Param("ageCategory") AgeCategory ageCategory,
      @Param("level") Integer level);

  @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Tournament t WHERE t.organization = :organization "
      +
      "AND t.type = :type " +
      "AND t.ageCategory = :ageCategory " +
      "AND t.level = :level " +
      "AND t.id <> :id")
  boolean existsByUniqueValuesAndIdNot(
      @Param("organization") Organization organization,
      @Param("type") TournamentType type,
      @Param("ageCategory") AgeCategory ageCategory,
      @Param("level") Integer level,
      @Param("id") Long id);
}
