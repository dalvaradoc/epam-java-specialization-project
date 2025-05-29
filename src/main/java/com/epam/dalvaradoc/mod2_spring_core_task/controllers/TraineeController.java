/* (C)2025 */
package com.epam.dalvaradoc.mod2_spring_core_task.controllers;

import com.epam.dalvaradoc.mod2_spring_core_task.dto.AuthenticationDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TraineeDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainerDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainingDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.UpdateTraineeDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.UpdateTraineeTrainersListDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.services.TraineeService;
import com.epam.dalvaradoc.mod2_spring_core_task.utils.SwaggerExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

@RestController
@RequestMapping("/trainees")
@Tag(name = "Trainee Management", description = "Endpoints for managing trainees")
public class TraineeController {

    private final TraineeService traineeService;

    @Autowired
    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @Operation(
            summary = "Register new trainee",
            description = "Creates a new trainee account",
            requestBody =
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "Trainee registration details",
                            content =
                                    @Content(
                                            mediaType = "application/json",
                                            examples =
                                                    @ExampleObject(
                                                            value =
                                                                    SwaggerExamples
                                                                            .TRAINEE_REGISTER_REQBODY))))
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Trainee successfully registered",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        examples =
                                                @ExampleObject(
                                                        value =
                                                                SwaggerExamples
                                                                        .GENERAL_AUTH_REQBODY))),
                @ApiResponse(responseCode = "400", description = "Invalid input data"),
            })
    @PostMapping
    public ResponseEntity<AuthenticationDTO> registerTrainee(@Valid @RequestBody TraineeDTO dto) {
        return ResponseEntity.ok(
                traineeService.createTrainee(
                        dto.getFirstName(),
                        dto.getLastName(),
                        dto.getAddress(),
                        dto.getBirthdate()));
    }

    @Operation(
            summary = "Get trainee by username",
            description = "Retrieves trainee information by username",
            requestBody =
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "Authentication details",
                            content =
                                    @Content(
                                            mediaType = "application/json",
                                            examples =
                                                    @ExampleObject(
                                                            value =
                                                                    SwaggerExamples
                                                                            .GENERAL_AUTH_REQBODY))))
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successfully retrieved trainee details",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        examples =
                                                @ExampleObject(
                                                        value =
                                                                SwaggerExamples
                                                                        .TRAINEE_GET_RESBODY))),
                @ApiResponse(responseCode = "401", description = "Bad credentials"),
            })
    @GetMapping("/{username}")
    public ResponseEntity<TraineeDTO> getTraineeByUsername(
            @PathVariable String username, @Valid @RequestBody AuthenticationDTO auth) {
        return ResponseEntity.ok(traineeService.getTraineeByUsername(auth));
    }

    @Operation(
            summary = "Update trainee",
            description = "Updates existing trainee information",
            requestBody =
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "Updated trainee details",
                            content =
                                    @Content(
                                            mediaType = "application/json",
                                            examples =
                                                    @ExampleObject(
                                                            value =
                                                                    SwaggerExamples
                                                                            .TRAINEE_PUT_REQBODY))))
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Trainee successfully updated",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        examples =
                                                @ExampleObject(
                                                        value =
                                                                SwaggerExamples
                                                                        .TRAINEE_PUT_RESBODY))),
                @ApiResponse(responseCode = "401", description = "Bad credentials"),
            })
    @PutMapping
    public ResponseEntity<TraineeDTO> updateTrainee(@Valid @RequestBody UpdateTraineeDTO dto) {
        return ResponseEntity.ok(traineeService.updateTrainee(dto, dto.getAuth()));
    }

    @Operation(
            summary = "Delete trainee",
            description = "Deletes trainee information",
            requestBody =
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "Authentication details",
                            content =
                                    @Content(
                                            mediaType = "application/json",
                                            examples =
                                                    @ExampleObject(
                                                            value =
                                                                    SwaggerExamples
                                                                            .GENERAL_AUTH_REQBODY))))
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Trainee successfully updated"),
                @ApiResponse(responseCode = "401", description = "Bad credentials"),
            })
    @DeleteMapping
    public ResponseEntity<Void> deleteTrainee(@Valid @RequestBody AuthenticationDTO auth) {
        traineeService.deleteTraineeByUsername(auth.getUsername(), auth.getPassword());
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Get trainers not assigned to trainee",
            description = "Retrieves a list of trainers not assigned to the specified trainee",
            requestBody =
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "Authentication details",
                            content =
                                    @Content(
                                            mediaType = "application/json",
                                            examples =
                                                    @ExampleObject(
                                                            value =
                                                                    SwaggerExamples
                                                                            .GENERAL_AUTH_REQBODY))))
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successfully retrieved trainers not assigned to trainee",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        examples =
                                                @ExampleObject(
                                                        value =
                                                                SwaggerExamples
                                                                        .TRAINEE_GET_NOT_ASIGNED_TRAINERS_RESBODY))),
                @ApiResponse(responseCode = "401", description = "Bad credentials"),
            })
    @GetMapping("/{username}/not-assigned-trainers")
    public ResponseEntity<List<TrainerDTO>> getTrainersNotAssignedToTrainee(
            @PathVariable String username, @Valid @RequestBody AuthenticationDTO auth) {
        return ResponseEntity.ok(traineeService.getTrainersNotAssignedToTrainee(auth));
    }

    @Operation(
            summary = "Add trainer to trainers list",
            description = "Adds a trainer to the list of trainers of trainee",
            requestBody =
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "Updated trainers list",
                            content =
                                    @Content(
                                            mediaType = "application/json",
                                            examples =
                                                    @ExampleObject(
                                                            value =
                                                                    SwaggerExamples
                                                                            .TRAINEE_ADD_TRAINER_TO_LIST_REQBODY))))
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Trainers list successfully updated",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        examples =
                                                @ExampleObject(
                                                        value =
                                                                SwaggerExamples
                                                                        .TRAINEE_ADD_TRAINER_TO_LIST_RESBODY))),
                @ApiResponse(responseCode = "401", description = "Bad credentials"),
            })
    @PutMapping("/{username}/trainers-list")
    public ResponseEntity<List<TrainerDTO>> updateTrainersList(
            @Valid @RequestBody UpdateTraineeTrainersListDTO dto) {
        return ResponseEntity.ok(
                traineeService.updateTrainersList(dto.getTrainersUsernames(), dto.getAuth()));
    }

    @Operation(
            summary = "Get trainings of trainee",
            description = "Retrieves a list of trainings of the specified trainee",
            requestBody =
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "Authentication details",
                            content =
                                    @Content(
                                            mediaType = "application/json",
                                            examples =
                                                    @ExampleObject(
                                                            value =
                                                                    SwaggerExamples
                                                                            .GENERAL_AUTH_REQBODY))))
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successfully retrieved trainings of trainee",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        examples =
                                                @ExampleObject(
                                                        value =
                                                                SwaggerExamples
                                                                        .TRAINEE_GET_TRAININGS_RESBODY))),
                @ApiResponse(responseCode = "401", description = "Bad credentials"),
            })
    @GetMapping("/{username}/trainings")
    public ResponseEntity<List<TrainingDTO>> getTrainings(
            @RequestParam(required = false) Date from,
            @RequestParam(required = false) Date to,
            @RequestParam(required = false) String trainerName,
            @RequestParam(required = false) String trainingType,
            @Valid @RequestBody AuthenticationDTO auth) {

        Map<String, Object> filters = new HashMap<>();
        filters.put("from", from);
        filters.put("to", to);
        filters.put("trainerName", trainerName);
        filters.put("trainingType", trainingType);
        return ResponseEntity.ok(traineeService.getTrainings(filters, auth));
    }

    @Operation(
            summary = "Change active state of trainee",
            description = "Changes the active state of the specified trainee",
            requestBody =
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "Authentication details",
                            content =
                                    @Content(
                                            mediaType = "application/json",
                                            examples =
                                                    @ExampleObject(
                                                            value =
                                                                    SwaggerExamples
                                                                            .GENERAL_AUTH_REQBODY))))
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Active state successfully changed"),
                @ApiResponse(responseCode = "401", description = "Bad credentials"),
            })
    @PatchMapping("/{username}/set-active-state")
    public ResponseEntity<Void> changeActiveState(
            @RequestParam boolean active, @Valid @RequestBody AuthenticationDTO auth) {
        traineeService.changeActiveState(active, auth);
        return ResponseEntity.ok().build();
    }
}
