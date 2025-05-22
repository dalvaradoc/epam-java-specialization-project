package com.epam.dalvaradoc.mod2_spring_core_task.dto;

import java.util.List;
import com.epam.dalvaradoc.mod2_spring_core_task.validations.NameLikeStringConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Data Transfer Object for Trainer information")
public class TrainerDTO {
  @Valid
  @Schema(description = "Authentication credentials")
  private AuthenticationDTO auth;

  @NotBlank(message = "First name is required")
  @NameLikeStringConstraint
  @Schema(description = "Trainer's first name", example = "John")
  private String firstName;

  @NotBlank(message = "Last name is required")
  @NameLikeStringConstraint
  @Schema(description = "Trainer's last name", example = "Smith")
  private String lastName;

  @Schema(description = "Whether the trainer account is active", example = "true")
  private Boolean isActive;

  @NotNull
  @Valid
  @Schema(description = "Trainer's specialization")
  private TrainingTypeDTO specialization;

  @Schema(description = "List of assigned trainees")
  private List<TraineeDTO> trainees;
}