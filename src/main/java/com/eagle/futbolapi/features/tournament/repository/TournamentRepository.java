package com.eagle.futbolapi.features.tournament.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.organization.entity.Organization;
import com.eagle.futbolapi.features.tournament.entity.Category;
import com.eagle.futbolapi.features.tournament.entity.Tournament;
import com.eagle.futbolapi.features.tournament.entity.TournamentType;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {

    Optional<Tournament> findByName(String name);

    Optional<Tournament> findByDisplayName(String displayName);

    List<Tournament> findByOrganization(Organization organization);

    List<Tournament> findByOrganizationId(Long organizationId);

    List<Tournament> findByType(TournamentType type);

    List<Tournament> findByCategory(Category category);

    List<Tournament> findByLevel(Integer level);

    List<Tournament> findByActive(Boolean active);

    List<Tournament> findByOrganizationIdAndActive(Long organizationId, Boolean active);

    List<Tournament> findByTypeAndCategory(TournamentType type, Category category);

    List<Tournament> findByOrganizationIdAndTypeAndLevel(Long organizationId, TournamentType type, Integer level);

}
