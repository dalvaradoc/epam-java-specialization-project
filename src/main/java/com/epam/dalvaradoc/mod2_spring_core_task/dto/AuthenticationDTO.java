package com.epam.dalvaradoc.mod2_spring_core_task.dto;

import org.hibernate.validator.constraints.Length;

import com.epam.dalvaradoc.mod2_spring_core_task.validations.UsernameConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Data Transfer Object for authentication credentials")
public class AuthenticationDTO {
  @UsernameConstraint
  @NotBlank(message = "Username is required")
  @Schema(description = "User's username", example = "john.doe", minLength = 3, maxLength = 50)
  private String username;

  @NotBlank(message = "Password is required")
  @Length(min = 8, max = 32, message = "Password must be between 8 and 32 characters")
  @Schema(description = "User's password", example = "Password123!", minLength = 8, maxLength = 32, format = "password")
  private String password;

  public AuthenticationDTO(String username, String password) {
    this.username = username;
    this.password = password;
  }
}