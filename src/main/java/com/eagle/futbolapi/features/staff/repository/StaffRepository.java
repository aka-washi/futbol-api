package com.eagle.futbolapi.features.staff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.enums.StaffRole;
import com.eagle.futbolapi.features.staff.entity.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

  boolean existsByPersonIdAndRole(Long personId, StaffRole role);

  @Query("SELECT COUNT(s) > 0 FROM Staff s WHERE s.person.id = :personId AND s.role = :role AND s.id != :id")
  boolean existsByPersonIdAndRoleAndIdNot(@Param("personId") Long personId, @Param("role") StaffRole role,
      @Param("id") Long id);

}
