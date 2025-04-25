package com.epam.dalvaradoc.mod2_spring_core_task.dto;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.TrainingType;
import com.epam.dalvaradoc.mod2_spring_core_task.validations.NameLikeStringConstraint;
import com.epam.dalvaradoc.mod2_spring_core_task.validations.UsernameConstraint;

import io.micrometer.common.lang.NonNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrainerDTO {
  //It should be validated with UUID, but for simplicity of test it can receive any id
  private String userId;
  @NameLikeStringConstraint
  private String firstName;
  @NameLikeStringConstraint
  private String lastName;
  @UsernameConstraint
  private String username;
  private String password;
  private boolean isActive;
  @NonNull
  private TrainingType specialization;
}
