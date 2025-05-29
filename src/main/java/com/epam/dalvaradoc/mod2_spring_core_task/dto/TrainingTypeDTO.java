/* (C)2025 */
package com.epam.dalvaradoc.mod2_spring_core_task.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Data Transfer Object for Training Type information")
public class TrainingTypeDTO {
    @Schema(description = "Training type ID", example = "1")
    private Long id;

    @NotNull(message = "Training type name is required")
    @Schema(
            description = "Name of the training type",
            example = "CARDIO",
            allowableValues = {
                "CARDIO",
                "STRENGTH",
                "FLEXIBILITY",
                "BALANCE",
                "CIRCUIT",
                "INTERVAL",
                "AEROBIC",
                "PLYOMETRICS"
            })
    private String name;

    public TrainingTypeDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
