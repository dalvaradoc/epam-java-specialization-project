package com.epam.dalvaradoc.mod2_spring_core_task.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Training;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.TrainingType;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.AuthenticationDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainingDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainingTypeDTO;
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

    @GetMapping("/types")
    public ResponseEntity<List<TrainingTypeDTO>> getAllTrainingTypes(@Valid @RequestBody AuthenticationDTO auth) {
      return ResponseEntity.ok(trainingService.getAllTrainingTypes(auth));
    }
}
