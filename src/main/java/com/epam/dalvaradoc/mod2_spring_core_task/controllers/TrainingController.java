package com.epam.dalvaradoc.mod2_spring_core_task.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainingDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.services.TrainingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/trainings")
public class TrainingController {
    private TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
      this.trainingService = trainingService;
    }

    @PostMapping
    public ResponseEntity<TrainingDTO> createTraining(@Valid @RequestBody TrainingDTO dto) {
        return ResponseEntity.ok(trainingService.createTraining(dto, dto.getAuth()));
    }
}
