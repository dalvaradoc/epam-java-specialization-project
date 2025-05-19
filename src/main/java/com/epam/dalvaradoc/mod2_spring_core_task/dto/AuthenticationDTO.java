package com.epam.dalvaradoc.mod2_spring_core_task.dto;

import com.epam.dalvaradoc.mod2_spring_core_task.validations.UsernameConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationDTO {
  @UsernameConstraint
  private String username;
  @NotNull
  @NotEmpty
  private String password;

  public AuthenticationDTO(String username, String password) {
    this.username = username;
    this.password = password;
  }
}
