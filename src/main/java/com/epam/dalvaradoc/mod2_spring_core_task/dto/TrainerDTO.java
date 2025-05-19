package com.epam.dalvaradoc.mod2_spring_core_task.dto;

import java.util.List;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.TrainingType;
import com.epam.dalvaradoc.mod2_spring_core_task.validations.NameLikeStringConstraint;
import com.epam.dalvaradoc.mod2_spring_core_task.validations.UsernameConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrainerDTO {
  @Valid
  AuthenticationDTO auth;
  @NameLikeStringConstraint
  private String firstName;
  @NameLikeStringConstraint
  private String lastName;
  private Boolean isActive;
  @NonNull
  private TrainingTypeDTO specialization;
  private List<TraineeDTO> trainees;
}
