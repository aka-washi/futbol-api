package com.eagle.futbolapi.features.staff.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.base.repository.BaseRepository;
import com.eagle.futbolapi.features.player.entity.Player;
import com.eagle.futbolapi.features.staff.entity.Staff;

/**
 * Repository interface for Staff entity data access operations.
 */
@Repository
public interface StaffRepository extends BaseRepository<Staff, Long> {

  Optional<Staff> findByPersonDisplayName(String displayName);

}
