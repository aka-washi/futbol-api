package com.eagle.futbolapi.features.team.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.enums.AgeCategory;
import com.eagle.futbolapi.features.base.enums.Gender;
import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.team.entity.Team;

@Repository
public interface TeamRepository extends BaseRepository<Team, Long> {

  Optional<Team> findByDisplayName(String displayName);

  Optional<Team> findByDisplayNameAndGenderAndAgeCategory(
      String displayName,
      Gender gender,
      AgeCategory ageCategory);

  Page<Team> findByGender(Gender gender, Pageable pageable);

  Page<Team> findByGenderAndAgeCategoryAndOrganizationId(
      Gender gender,
      AgeCategory ageCategory,
      Long organizationId,
      Pageable pageable);

  Page<Team> findByGenderAndAgeCategoryAndCountryId(
      Gender gender,
      AgeCategory ageCategory,
      Long countryId,
      Pageable pageable);

  boolean existsByDisplayName(String displayName);

  boolean existsByDisplayNameAndIdNot(String displayName, Long id);

  boolean existsByNameAndGenderAndAgeCategoryAndOrganizationId(
      String name,
      Gender gender,
      AgeCategory ageCategory,
      Long organizationId);

  boolean existsByNameAndGenderAndAgeCategoryAndOrganizationIdAndIdNot(
      String name,
      Gender gender,
      AgeCategory ageCategory,
      Long organizationId,
      Long id);
}
