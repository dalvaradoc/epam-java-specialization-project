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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

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

    @Operation(summary = "Get trainer by username", description = "Retrieves trainer information by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved trainer details", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                        {
                            "firstName": "John",
                            "lastName": "Doe",
                            "specialization": {
                                "name": "CARDIO"
                            },
                            "isActive": true,
                            "trainees": [
                                {
                                    "firstName": "Jane",
                                    "lastName": "Smith",
                                    "auth": {
                                        "username": "jane.smith"
                                    }
                                }
                            ]
                        }
                    """))),
            @ApiResponse(responseCode = "401", description = "Bad credentials"),
    })
    @GetMapping("/{username}")
    public ResponseEntity<TrainerDTO> getTrainerByUsername(@Valid @RequestBody AuthenticationDTO auth) {
        return ResponseEntity.ok(trainerService.getTrainerByUsername(auth));
    }

    @Operation(summary = "Register new trainer", description = "Creates a new trainer account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainer successfully registered", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                        {
                            "username": "john.doe",
                            "password": "generated_password"
                        }
                    """))),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<AuthenticationDTO> registerTrainer(@Valid @RequestBody TrainerDTO dto) {
        return ResponseEntity.ok(trainerService.createTrainer(
                dto.getFirstName(), dto.getLastName(), dto.getSpecialization().getName()));
    }

    @Operation(summary = "Update trainer", description = "Updates existing trainer information", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Trainer data to be updated", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                {
                    "firstName": "Jhon",
                    "lastName": "Verick",
                    "isActive": true,
                    "specialization": "CARDIO",
                    "auth": {
                        "username": "Brady.Verick",
                        "password": "password"
                    }
                }
            """))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainer successfully updated", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                        "auth": {
                            "username": "Brady.Verick"
                        },
                        "firstName": "Jhon",
                        "lastName": "Verick",
                        "isActive": true,
                        "specialization": {
                            "name": "CARDIO"
                        },
                        "trainees": [
                            {
                                "auth": {
                                    "username": "Cobby.Castagneri"
                                },
                                "firstName": "Cobby",
                                "lastName": "Castagneri"
                            },
                            {
                                "auth": {
                                    "username": "Lenard.Pedgrift"
                                },
                                "firstName": "Lenard",
                                "lastName": "Pedgrift"
                            },
                        ]
                    }
                    """))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Bad credentials")
    })
    @PutMapping
    public ResponseEntity<TrainerDTO> updateTrainer(@Valid @RequestBody UpdateTrainerDTO dto) {
        return ResponseEntity.ok(trainerService.updateTrainer(dto, dto.getAuth()));
    }

    @Operation(summary = "Get trainings of trainer", description = "Retrieves a list of trainings for the specified trainer filtered by date range, trainee name and training type", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Authentication data", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                {
                    "username": "john.doe",
                    "password": "generated_password"
                }
            """))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved trainer's trainings", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    [
                        {
                            "trainee": {
                                "firstName": "Peggie",
                                "lastName": "Barthelemy"
                            },
                            "name": "erat nulla tempus vivamus",
                            "type": {
                                "name": "INTERVAL"
                            },
                            "date": "2024-12-11",
                            "duration": 42
                        }
                    ]
                    """))),
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

    @Operation(summary = "Change active state of trainer", description = "Updates the active/inactive status of the trainer")
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