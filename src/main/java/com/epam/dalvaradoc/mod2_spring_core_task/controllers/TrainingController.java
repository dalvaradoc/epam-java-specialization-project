/* (C)2025 */
package com.epam.dalvaradoc.mod2_spring_core_task.controllers;

import com.epam.dalvaradoc.mod2_spring_core_task.dto.AuthenticationDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainingDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainingTypeDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.services.TrainingService;
import com.epam.dalvaradoc.mod2_spring_core_task.utils.SwaggerExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trainings")
@Tag(name = "Training Management", description = "Endpoints for managing trainings")
public class TrainingController {
    private TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @Operation(
            summary = "Create new training",
            description = "Creates a new training session",
            requestBody =
                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            description = "Training details",
                            content =
                                    @Content(
                                            mediaType = "application/json",
                                            examples =
                                                    @ExampleObject(
                                                            value =
                                                                    SwaggerExamples
                                                                            .TRAINING_CREATE_TRAINING_REQBODY))))
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Training successfully created",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        examples =
                                                @ExampleObject(
                                                        value =
                                                                SwaggerExamples
                                                                        .TRAINING_CREATE_TRAINING_RESBODY))),
                @ApiResponse(responseCode = "400", description = "Invalid input data"),
                @ApiResponse(responseCode = "401", description = "Bad credentials")
            })
    @PostMapping
    public ResponseEntity<TrainingDTO> createTraining(@Valid @RequestBody TrainingDTO dto) {
        return ResponseEntity.ok(trainingService.createTraining(dto, dto.getAuth()));
    }

    @GetMapping("/types")
    @Operation(
            summary = "Get all training types",
            description = "Retrieves a list of all available training types",
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
                        description = "Successfully retrieved training types",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        examples =
                                                @ExampleObject(
                                                        value =
                                                                SwaggerExamples
                                                                        .TRAINING_GET_TRAINING_TYPES_RESBODY))),
                @ApiResponse(responseCode = "401", description = "Bad credentials")
            })
    public ResponseEntity<List<TrainingTypeDTO>> getAllTrainingTypes(
            @Valid @RequestBody AuthenticationDTO auth) {
        return ResponseEntity.ok(trainingService.getAllTrainingTypes(auth));
    }
}
