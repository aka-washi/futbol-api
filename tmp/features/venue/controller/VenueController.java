package com.eagle.futbolapi.features.venue.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.shared.controller.BaseCrudController;
import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.venue.dto.VenueDTO;
import com.eagle.futbolapi.features.venue.entity.Venue;
import com.eagle.futbolapi.features.venue.mapper.VenueMapper;
import com.eagle.futbolapi.features.venue.service.VenueService;

@RestController
@RequestMapping("/venues")
@Validated
public class VenueController extends BaseCrudController<Venue, VenueDTO, VenueService, VenueMapper> {

    public VenueController(VenueService venueService, VenueMapper venueMapper) {
        super(
                venueService,
                venueMapper,
                "Venue",
                "Venue retrieved successfully",
                "Venue already exists",
                "SERVER_ERROR");
    }

    // Implement abstract methods from BaseCrudController
    @Override
    protected Page<Venue> getAllEntities(Pageable pageable) {
        return service.getAll(pageable);
    }

    @Override
    protected Venue getEntityById(Long id) {
        return service.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException(resourceName, "id", id));
    }

    @Override
    protected Venue createEntity(Venue entity) {
        return service.create(entity);
    }

    @Override
    protected Venue updateEntity(Long id, Venue entity) {
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
    protected VenueDTO toDTO(Venue entity) {
        return mapper.toVenueDTO(entity);
    }

    @Override
    protected Venue toEntity(VenueDTO dto) {
        return mapper.toVenue(dto);
    }
}
