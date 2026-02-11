package com.eagle.futbolapi.features.organization.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.base.enums.UniquenessStrategy;
import com.eagle.futbolapi.features.base.service.BaseCrudService;
import com.eagle.futbolapi.features.organization.dto.OrganizationDto;
import com.eagle.futbolapi.features.organization.entity.Organization;
import com.eagle.futbolapi.features.organization.mapper.OrganizationMapper;
import com.eagle.futbolapi.features.organization.repository.OrganizationRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class OrganizationService extends BaseCrudService<Organization, Long, OrganizationDto> {

  protected OrganizationService(OrganizationRepository repository, OrganizationMapper mapper) {
    super(repository, mapper);
  }

  @Override
  protected boolean isDuplicate(@NotNull Organization organization) {
    Objects.requireNonNull(organization, "Organization cannot be null");

    return isDuplicate(organization, UniquenessStrategy.ALL);
  }

  @Override
  protected boolean isDuplicate(@NotNull Long id, @NotNull Organization organization) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(organization, "Organization cannot be null");

    return isDuplicate(id, organization, UniquenessStrategy.ALL);
  }

}
