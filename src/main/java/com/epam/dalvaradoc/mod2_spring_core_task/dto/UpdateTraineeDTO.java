package com.epam.dalvaradoc.mod2_spring_core_task.dto;

import java.sql.Date;
import java.util.List;

import com.epam.dalvaradoc.mod2_spring_core_task.validations.NameLikeStringConstraint;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class UpdateTraineeDTO {
  @NameLikeStringConstraint
  private String firstName;
  @NameLikeStringConstraint
  private String lastName;
  @NonNull
  private Boolean isActive;
  @Past
  @Nullable
  private Date birthdate;
  @Nullable
  private String address;
  @Valid
  AuthenticationDTO auth;
}
