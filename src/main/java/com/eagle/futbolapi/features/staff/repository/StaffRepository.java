package com.eagle.futbolapi.features.staff.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.enums.StaffRole;
import com.eagle.futbolapi.features.person.entity.Person;
import com.eagle.futbolapi.features.staff.entity.Staff;
import com.eagle.futbolapi.features.team.entity.Team;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

}
