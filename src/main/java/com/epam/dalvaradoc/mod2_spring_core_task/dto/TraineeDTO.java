package com.epam.dalvaradoc.mod2_spring_core_task.dto;

import java.sql.Date;

import com.epam.dalvaradoc.mod2_spring_core_task.validations.NameLikeStringConstraint;
import com.epam.dalvaradoc.mod2_spring_core_task.validations.UsernameConstraint;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TraineeDTO {
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
  @Past
  private Date birthdate;
  @NonNull
  private String address;
}
