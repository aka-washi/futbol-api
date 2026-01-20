package com.eagle.futbolapi.features.competition.controller;

import java.util.List;

import jakarta.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.futbolapi.features.competition.dto.CompetitionDTO;
import com.eagle.futbolapi.features.competition.entity.Competition;
import com.eagle.futbolapi.features.competition.mapper.CompetitionMapper;
import com.eagle.futbolapi.features.competition.service.CompetitionService;
import com.eagle.futbolapi.features.base.dto.ApiResponse;
import com.eagle.futbolapi.features.base.enums.CompetitionType;
import com.eagle.futbolapi.features.base.util.ResponseUtil;
import com.eagle.futbolapi.features.base.controller.BaseCrudController;
import com.eagle.futbolapi.features.base.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/competitions")
@Validated
public class CompetitionController
        extends BaseCrudController<Competition, CompetitionDTO, CompetitionService, CompetitionMapper> {
}
