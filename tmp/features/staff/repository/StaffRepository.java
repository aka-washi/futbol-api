package com.eagle.futbolapi.features.staff.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.person.entity.Person;
import com.eagle.futbolapi.features.staff.entity.Staff;
import com.eagle.futbolapi.features.staff.entity.StaffRole;
import com.eagle.futbolapi.features.team.entity.Team;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    Optional<Staff> findByPerson(Person person);

    Optional<Staff> findByPersonId(Long personId);

    List<Staff> findByRole(StaffRole role);

    List<Staff> findByCurrentTeam(Team currentTeam);

    List<Staff> findByCurrentTeamId(Long currentTeamId);

    List<Staff> findByActive(Boolean active);

    List<Staff> findByCurrentTeamIdAndRole(Long currentTeamId, StaffRole role);

    List<Staff> findByCurrentTeamIdAndActive(Long currentTeamId, Boolean active);

    List<Staff> findByRoleAndActive(StaffRole role, Boolean active);

    List<Staff> findByCurrentTeamIdAndRoleAndActive(Long currentTeamId, StaffRole role, Boolean active);

    boolean existsByPersonId(Long personId);

}
