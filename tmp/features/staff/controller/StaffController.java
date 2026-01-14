package com.eagle.futbolapi.features.staff.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.shared.controller.BaseCrudController;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.staff.dto.StaffDTO;
import com.eagle.futbolapi.features.staff.entity.Staff;
import com.eagle.futbolapi.features.staff.mapper.StaffMapper;
import com.eagle.futbolapi.features.staff.service.StaffService;

@RestController
@RequestMapping("/staff")
@Validated
public class StaffController extends BaseCrudController<Staff, StaffDTO, StaffService, StaffMapper> {

    public StaffController(StaffService staffService, StaffMapper staffMapper) {
        super(
                staffService,
                staffMapper,
                "Staff",
                "Staff retrieved successfully",
                "Staff already exists",
                "SERVER_ERROR");
    }

    // Implement abstract methods from BaseCrudController
    @Override
    protected Page<Staff> getAllEntities(Pageable pageable) {
        return service.getAll(pageable);
    }

    @Override
    protected Staff getEntityById(Long id) {
        return service.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException(resourceName, "id", id));
    }

    @Override
    protected Staff createEntity(Staff entity) {
        return service.create(entity);
    }

    @Override
    protected Staff updateEntity(Long id, Staff entity) {
        return service.update(id, entity);
    }

    @Override
    protected void deleteEntity(Long id) {
        service.delete(id);
    }

    @Override
    protected boolean existsById(Long id) {
        return service.existsById(id);
    }

    @Override
    protected StaffDTO toDTO(Staff entity) {
        return mapper.toStaffDTO(entity);
    }

    @Override
    protected Staff toEntity(StaffDTO dto) {
        return mapper.toStaff(dto);
    }
}
