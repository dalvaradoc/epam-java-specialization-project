package com.epam.dalvaradoc.mod2_spring_core_task.dto;

import java.sql.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Data Transfer Object for Training information")
public class TrainingDTO {
  @Valid
  @Schema(description = "Authentication credentials")
  private AuthenticationDTO auth;

  @NotNull(message = "Trainee information is required")
  @Schema(description = "Training's assigned trainee")
  private TraineeDTO trainee;

  @NotNull(message = "Trainer information is required")
  @Schema(description = "Training's assigned trainer")
  private TrainerDTO trainer;

  @NotNull(message = "Training name is required")
  @Length(min = 2, max = 50, message = "Training name must be between 2 and 50 characters")
  @Schema(description = "Name of the training session", example = "Morning Cardio Session")
  private String name;

  @NotNull(message = "Training type is required")
  @Schema(description = "Type of training")
  private TrainingTypeDTO type;

  @NotNull(message = "Training date is required")
  @FutureOrPresent(message = "Training date must be today or in the future")
  @Schema(description = "Date of the training session", example = "2024-05-22")
  private Date date;

  @Min(value = 1, message = "Duration must be at least 1 minute")
  @Schema(description = "Duration of training in minutes", example = "60")
  private int duration;
}