package com.eagle.futbolapi.features.staff.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.shared.service.BaseCrudService;
import com.eagle.futbolapi.features.staff.entity.Staff;
import com.eagle.futbolapi.features.staff.repository.StaffRepository;

@Service
@Transactional
public class StaffService extends BaseCrudService<Staff, Long> {

    private final StaffRepository staffRepository;

    public StaffService(StaffRepository staffRepository) {
        super(staffRepository);
        this.staffRepository = staffRepository;
    }

    @Override
    public Staff update(Long id, Staff staff) {
        Staff existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entity with given ID does not exist"));
        staff.setCreatedAt(existing.getCreatedAt());
        staff.setId(id);
        return super.update(id, staff);
    }

    @Override
    protected boolean isDuplicate(@NotNull Staff staff) {
        Objects.requireNonNull(staff, "Staff cannot be null");

        // Staff members can have similar roles across different teams
        // For now, no strict duplicate check - business logic specific
        return false;
    }

    @Override
    protected boolean isDuplicate(Long id, @NotNull Staff staff) {
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(staff, "Staff details cannot be null");

        Staff existingStaff = getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff", "id", id));

        // For updates, no strict duplicate check - business logic specific
        return false;
    }
}
