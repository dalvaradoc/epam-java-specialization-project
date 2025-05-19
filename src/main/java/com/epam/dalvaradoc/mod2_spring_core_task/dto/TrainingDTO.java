package com.epam.dalvaradoc.mod2_spring_core_task.dto;

import java.sql.Date;

import org.hibernate.validator.constraints.Length;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainee;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainer;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.TrainingType;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrainingDTO {
  private AuthenticationDTO auth;
  @NotNull
  private TraineeDTO trainee;
  @NotNull
  private TrainerDTO trainer;
  @Length(min = 2, max = 50, message = "Training name must be between 2 and 50 characters")
  private String name;
  @NotNull
  private TrainingTypeDTO type;
  private Date date;
  @Min(value = 1, message = "Duration must be at least 1")
  private int duration;
}
