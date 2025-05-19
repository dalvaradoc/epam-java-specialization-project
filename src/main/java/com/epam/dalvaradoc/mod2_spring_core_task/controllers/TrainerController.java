package com.epam.dalvaradoc.mod2_spring_core_task.controllers;

import com.epam.dalvaradoc.mod2_spring_core_task.dto.AuthenticationDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainerDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.services.TrainerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}")
    public ResponseEntity<TrainerDTO> getTrainerById(@PathVariable String id) {
        return ResponseEntity.ok(trainerService.getTrainerById(id, null, null));
    }

    @PostMapping
    public ResponseEntity<AuthenticationDTO> registerTrainer(@Valid @RequestBody TrainerDTO dto) {
        return ResponseEntity.ok(trainerService.createTrainer(
                dto.getFirstName(), dto.getLastName(), dto.getSpecialization()).getAuth());
    }

    // @PutMapping
    // public ResponseEntity<Boolean> updateTrainer(@Valid @RequestBody TrainerDTO dto) {
    //     return ResponseEntity.ok(trainerService.updateTrainer(dto));
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deleteTrainer(@PathVariable String id) {
    //     trainerService.deleteTrainerById(id, null, null);
    //     return ResponseEntity.noContent().build();
    // }
}