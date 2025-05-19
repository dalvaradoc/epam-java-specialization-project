package com.epam.dalvaradoc.mod2_spring_core_task.dto;

import java.util.List;

import com.epam.dalvaradoc.mod2_spring_core_task.validations.UsernameConstraint;

import jakarta.validation.Valid;
import lombok.Getter;

@Getter
public class UpdateTraineeTrainersListDTO {
  @Valid
  private AuthenticationDTO auth;
  private List<@UsernameConstraint String> trainersUsernames;
}
