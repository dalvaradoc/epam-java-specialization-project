package com.epam.dalvaradoc.mod2_spring_core_task.controllers;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.dalvaradoc.mod2_spring_core_task.dto.AuthenticationDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainerDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainingDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.UpdateTrainerDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.services.TrainerService;
import com.epam.dalvaradoc.mod2_spring_core_task.utils.SwaggerExamples;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/trainers")
@Tag(name = "Trainer Management", description = "Endpoints for managing trainers")
public class TrainerController {

    private final TrainerService trainerService;

    @Autowired
    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @Operation(summary = "Get all trainers", description = "This endpoint is for tests only. Retrieves a list of all registered trainers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved trainers list"),
    })
    @GetMapping
    public ResponseEntity<List<TrainerDTO>> getAllTrainers() {
        return ResponseEntity.ok(trainerService.getAllTrainers());
    }

    @Operation(summary = "Get trainer by username", description = "Retrieves trainer information by username", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Auth credentials of Trainer", content = @Content(mediaType = "application/json", examples = @ExampleObject(SwaggerExamples.GENERAL_AUTH_REQBODY))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved trainer details", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExamples.TRAINER_GET_RESBODY))),
            @ApiResponse(responseCode = "401", description = "Bad credentials"),
    })
    @GetMapping("/{username}")
    public ResponseEntity<TrainerDTO> getTrainerByUsername(@Valid @RequestBody AuthenticationDTO auth) {
        return ResponseEntity.ok(trainerService.getTrainerByUsername(auth));
    }

    @Operation(summary = "Register new trainer", description = "Creates a new trainer account", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Data to create a Trainer", content = @Content(mediaType = "application/json", examples = @ExampleObject(SwaggerExamples.TRAINER_REGISTER_REQBODY))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainer successfully registered", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExamples.TRAINER_REGISTER_RESBODY))),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<AuthenticationDTO> registerTrainer(@Valid @RequestBody TrainerDTO dto) {
        return ResponseEntity.ok(trainerService.createTrainer(
                dto.getFirstName(), dto.getLastName(), dto.getSpecialization().getName()));
    }

    @Operation(summary = "Update trainer", description = "Updates existing trainer information", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Trainer data to be updated", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExamples.TRAINER_PUT_REQBODY))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainer successfully updated", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExamples.TRAINER_PUT_RESBODY))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Bad credentials")
    })
    @PutMapping
    public ResponseEntity<TrainerDTO> updateTrainer(@Valid @RequestBody UpdateTrainerDTO dto) {
        return ResponseEntity.ok(trainerService.updateTrainer(dto, dto.getAuth()));
    }

    @Operation(summary = "Get trainings of trainer", description = "Retrieves a list of trainings for the specified trainer filtered by date range, trainee name and training type", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Authentication data", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExamples.TRAINER_REGISTER_RESBODY))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved trainer's trainings", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExamples.TRAINER_GET_TRAININGS_RESBODY))),
            @ApiResponse(responseCode = "401", description = "Bad credentials")
    })
    @GetMapping("/{username}/trainings")
    public ResponseEntity<List<TrainingDTO>> getTrainings(@RequestParam(required = false) Date from,
            @RequestParam(required = false) Date to, @RequestParam(required = false) String traineeName,
            @RequestParam(required = false) String trainingType, @Valid @RequestBody AuthenticationDTO auth) {

        Map<String, Object> filters = new HashMap<>();
        filters.put("from", from);
        filters.put("to", to);
        filters.put("traineeName", traineeName);
        filters.put("trainingType", trainingType);
        return ResponseEntity.ok(trainerService.getTrainings(filters, auth));
    }

    @Operation(summary = "Change active state of trainer", description = "Updates the active/inactive status of the trainer", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Authentication data", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExamples.TRAINER_REGISTER_RESBODY))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Active state successfully changed"),
            @ApiResponse(responseCode = "401", description = "Bad credentials")
    })
    @PatchMapping("/{username}/set-active-state")
    public ResponseEntity<Void> changeActiveState(@RequestParam boolean active,
            @Valid @RequestBody AuthenticationDTO auth) {
        trainerService.changeActiveState(active, auth);
        return ResponseEntity.ok().build();
    }
}