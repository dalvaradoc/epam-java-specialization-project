package com.epam.dalvaradoc.mod2_spring_core_task.dto;

import com.epam.dalvaradoc.mod2_spring_core_task.validations.NameLikeStringConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Data Transfer Object for updating Trainer information")
public class UpdateTrainerDTO {
  @Valid
  @NotNull(message = "Authentication details are required")
  @Schema(description = "Authentication credentials")
  private AuthenticationDTO auth;

  @NameLikeStringConstraint
  @Schema(description = "Updated first name", example = "John")
  private String firstName;

  @NameLikeStringConstraint
  @Schema(description = "Updated last name", example = "Smith")
  private String lastName;

  @NotNull(message = "Active status must be specified")
  @Schema(description = "Updated active status", example = "true")
  private Boolean isActive;

  @NonNull
  @NotEmpty(message = "Specialization is required")
  @Schema(description = "Updated specialization type", example = "CARDIO", allowableValues = { "CARDIO", "STRENGTH",
      "FLEXIBILITY", "BALANCE", "CIRCUIT", "INTERVAL", "AEROBIC", "PLYOMETRICS" })
  private String specialization;
}