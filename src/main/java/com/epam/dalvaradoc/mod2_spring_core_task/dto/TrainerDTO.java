package com.epam.dalvaradoc.mod2_spring_core_task.dto;

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
  //It should be validated with UUID, but for simplicity of test it can receive any id
  private String userId;
  @NameLikeStringConstraint
  private String firstName;
  @NameLikeStringConstraint
  private String lastName;
  private Boolean isActive;
  @NonNull
  private TrainingTypeDTO specialization;
  @Valid
  AuthenticationDTO auth;
}
