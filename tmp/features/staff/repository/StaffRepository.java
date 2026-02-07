package com.eagle.futbolapi.features.staff.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.staff.entity.Staff;

@Repository
public interface StaffRepository extends BaseRepository<Staff, Long> {

  Optional<Staff> findByPersonId(Long personId);

  Optional<Staff> findByPersonDisplayName(String displayName);

  boolean existsByPersonId(Long personId);

  @Query("SELECT COUNT(s) > 0 FROM Staff s WHERE s.person.id = :personId AND s.id != :id")
  boolean existsByPersonIdAndIdNot(@Param("personId") Long personId, @Param("id") Long id);

}
