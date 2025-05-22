package com.epam.dalvaradoc.mod2_spring_core_task.controllers;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.dalvaradoc.mod2_spring_core_task.dto.AuthenticationDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TraineeDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TraineeMapper;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainerDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainingDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.UpdateTraineeDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.UpdateTraineeTrainersListDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.services.TraineeService;
import com.epam.dalvaradoc.mod2_spring_core_task.services.TrainingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/trainees")
@Tag(name = "Trainee Management", description = "Endpoints for managing trainees")
public class TraineeController {

    private final TraineeService traineeService;
    private final TrainingService trainingService;

    private final TraineeMapper mapper = new TraineeMapper();

    @Autowired
    public TraineeController(TraineeService traineeService, TrainingService trainingService) {
        this.traineeService = traineeService;
        this.trainingService = trainingService;
    }

    @GetMapping
    public ResponseEntity<List<TraineeDTO>> getAllTrainees() {
        return ResponseEntity.ok(traineeService.getAllTrainees());
    }

    // @Operation(summary = "Get all trainees", description = "Retrieves a list of
    // all registered trainees")
    // @ApiResponses(value = {
    // @ApiResponse(responseCode = "200", description = "Successfully retrieved
    // trainees list"),
    // @ApiResponse(responseCode = "401", description = "Unauthorized"),
    // @ApiResponse(responseCode = "403", description = "Forbidden")
    // })
    @PostMapping
    public ResponseEntity<AuthenticationDTO> registerTrainee(@Valid @RequestBody TraineeDTO dto) {
        return ResponseEntity.ok(traineeService.createTrainee(dto.getFirstName(), dto.getLastName(), dto.getAddress(),
                dto.getBirthdate()));
    }

    @GetMapping("/{username}")
    public ResponseEntity<TraineeDTO> getTraineeByUsername(@PathVariable String username,
            @Valid @RequestBody AuthenticationDTO auth) {
        return ResponseEntity.ok(traineeService.getTraineeByUsername(auth));
    }

    @PutMapping
    public ResponseEntity<TraineeDTO> updateTrainee(@Valid @RequestBody UpdateTraineeDTO dto) {
        return ResponseEntity.ok(traineeService.updateTrainee(dto, dto.getAuth()));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTrainee(@Valid @RequestBody AuthenticationDTO auth) {
        traineeService.deleteTraineeByUsername(auth.getUsername(), auth.getPassword());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{username}/not-assigned-trainers")
    public ResponseEntity<List<TrainerDTO>> getTrainersNotAssignedToTrainee(@PathVariable String username,
            @Valid @RequestBody AuthenticationDTO auth) {
        return ResponseEntity.ok(traineeService.getTrainersNotAssignedToTrainee(auth));
    }

    @PutMapping("/{username}/trainers-list")
    public ResponseEntity<List<TrainerDTO>> updateTrainersList(@Valid @RequestBody UpdateTraineeTrainersListDTO dto) {
        return ResponseEntity.ok(traineeService.updateTrainersList(dto.getTrainersUsernames(), dto.getAuth()));
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
        return ResponseEntity.ok(traineeService.getTrainings(filters, auth));
    }

    @PatchMapping("/{username}/set-active-state")
    public ResponseEntity<Void> changeActiveState(@RequestParam boolean active,
            @Valid @RequestBody AuthenticationDTO auth) {
        traineeService.changeActiveState(active, auth);
        return ResponseEntity.ok().build();
    }
}