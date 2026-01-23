package com.eagle.futbolapi.features.staff.service;

import jakarta.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.mapper.BaseMapper;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.staff.dto.StaffDTO;
import com.eagle.futbolapi.features.staff.entity.Staff;

@Service
@Transactional
public class StaffService extends BaseCrudService<Staff, Long, StaffDTO> {

  protected StaffService(JpaRepository<Staff, Long> repository, BaseMapper<Staff, StaffDTO> mapper) {
    super(repository, mapper);
    // TODO Auto-generated constructor stub
  }

  @Override
  protected boolean isDuplicate(@NotNull Staff entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isDuplicate'");
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Staff entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isDuplicate'");
  }

}
