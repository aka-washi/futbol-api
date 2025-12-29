package com.eagle.futbolapi.features.staff.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eagle.futbolapi.features.staff.entity.Staff;

public interface StaffRepository extends JpaRepository<Staff, Long> {

}
