package com.eagle.futbolapi.features.venue.service;

import java.util.Objects;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.shared.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.shared.service.BaseCrudService;
import com.eagle.futbolapi.features.venue.entity.Venue;
import com.eagle.futbolapi.features.venue.repository.VenueRepository;

@Service
@Transactional
public class VenueService extends BaseCrudService<Venue, Long> {

    private final VenueRepository venueRepository;

    public VenueService(VenueRepository venueRepository) {
        super(venueRepository);
        this.venueRepository = venueRepository;
    }

    public Optional<Venue> getVenueByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Venue name cannot be null or empty");
        }
        return venueRepository.findByName(name);
    }

    public Optional<Venue> getVenueByDisplayName(String displayName) {
        if (displayName == null || displayName.trim().isEmpty()) {
            throw new IllegalArgumentException("Venue display name cannot be null or empty");
        }
        return venueRepository.findByDisplayName(displayName);
    }

    @Override
    public Venue update(Long id, Venue venue) {
        Venue existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entity with given ID does not exist"));
        venue.setCreatedAt(existing.getCreatedAt());
        venue.setId(id);
        return super.update(id, venue);
    }

    @Override
    protected boolean isDuplicate(@NotNull Venue venue) {
        Objects.requireNonNull(venue, "Venue cannot be null");

        // Venues could theoretically have same names in different cities
        // For now, no strict duplicate check - business logic specific
        return false;
    }

    @Override
    protected boolean isDuplicate(Long id, @NotNull Venue venue) {
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(venue, "Venue details cannot be null");

        Venue existingVenue = getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venue", "id", id));

        // For updates, no strict duplicate check - business logic specific
        return false;
    }
}
