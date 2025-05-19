package com.epam.dalvaradoc.mod2_spring_core_task.dto;

import java.sql.Date;
import java.util.List;

import com.epam.dalvaradoc.mod2_spring_core_task.validations.NameLikeStringConstraint;
import com.epam.dalvaradoc.mod2_spring_core_task.validations.UsernameConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TraineeDTO {
  //It should be validated with UUID, but for simplicity of test it can receive any id
  private String userId;
  @Valid
  AuthenticationDTO auth;
  @NameLikeStringConstraint
  private String firstName;
  @NameLikeStringConstraint
  private String lastName;
  private Boolean isActive;
  @Past
  private Date birthdate;
  @NonNull
  private String address;
  private List<TrainerDTO> trainers;
}
