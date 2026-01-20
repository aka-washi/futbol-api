package com.eagle.futbolapi.features.outcome.service;

import java.util.Objects;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eagle.futbolapi.features.outcome.entity.Outcome;
import com.eagle.futbolapi.features.outcome.repository.OutcomeRepository;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;
import com.eagle.futbolapi.features.base.service.BaseCrudService;

@Service
@Transactional
public class OutcomeService extends BaseCrudService<Outcome, Long> {

}
