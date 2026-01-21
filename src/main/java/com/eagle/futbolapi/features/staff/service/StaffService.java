package com.eagle.futbolapi.features.staff.service;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.staff.entity.Staff;

@Service
@Transactional
public class StaffService extends BaseCrudService<Staff, Long> {

}
