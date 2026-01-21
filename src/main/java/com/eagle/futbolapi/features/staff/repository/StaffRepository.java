package com.eagle.futbolapi.features.staff.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eagle.futbolapi.features.staff.entity.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

}
