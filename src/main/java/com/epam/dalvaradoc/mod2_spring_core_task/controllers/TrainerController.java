package com.epam.dalvaradoc.mod2_spring_core_task.controllers;

import com.epam.dalvaradoc.mod2_spring_core_task.dto.AuthenticationDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainerDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainingDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainingTypeDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.UpdateTrainerDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.services.TrainerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trainers")
public class TrainerController {

    private final TrainerService trainerService;

    @Autowired
    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @GetMapping
    public ResponseEntity<List<TrainerDTO>> getAllTrainers() {
        return ResponseEntity.ok(trainerService.getAllTrainers());
    }

    @GetMapping("/{username}")
    public ResponseEntity<TrainerDTO> getTrainerByUsername(@Valid @RequestBody AuthenticationDTO auth) {
        return ResponseEntity.ok(trainerService.getTrainerByUsername(auth));
    }

    @PostMapping
    public ResponseEntity<AuthenticationDTO> registerTrainer(@Valid @RequestBody TrainerDTO dto) {
        return ResponseEntity.ok(trainerService.createTrainer(
                dto.getFirstName(), dto.getLastName(), dto.getSpecialization().getName()));
    }

    @PutMapping
    public ResponseEntity<TrainerDTO> updateTrainer(@Valid @RequestBody UpdateTrainerDTO dto) {
        return ResponseEntity.ok(trainerService.updateTrainer(dto, dto.getAuth()));
    }

    @GetMapping("/{username}/trainings")
    public ResponseEntity<List<TrainingDTO>> getTrainings(@RequestParam(required = false) Date from,
            @RequestParam(required = false) Date to, @RequestParam(required = false) String trainerName,
            @RequestParam(required = false) String trainingType, @Valid @RequestBody AuthenticationDTO auth) {

        Map<String, Object> filters = new HashMap<>();
        filters.put("from", from);
        filters.put("to", to);
        filters.put("trainerName", trainerName);
        filters.put("trainingType", trainingType);
        return ResponseEntity.ok(trainerService.getTrainings(filters, auth));
    }
}