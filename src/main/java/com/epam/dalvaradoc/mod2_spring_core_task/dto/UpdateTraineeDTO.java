/* (C)2025 */
package com.epam.dalvaradoc.mod2_spring_core_task.dto;

import com.epam.dalvaradoc.mod2_spring_core_task.validations.NameLikeStringConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import java.sql.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Data Transfer Object for updating Trainee information")
public class UpdateTraineeDTO {
    @NotBlank(message = "First name is required")
    @NameLikeStringConstraint
    @Schema(description = "Updated first name", example = "John")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @NameLikeStringConstraint
    @Schema(description = "Updated last name", example = "Doe")
    private String lastName;

    @NotNull(message = "Active status must be specified")
    @Schema(description = "Updated active status", example = "true")
    private Boolean isActive;

    @Past(message = "Birthdate must be in the past")
    @Schema(description = "Updated date of birth", example = "1990-01-01")
    private Date birthdate;

    @Schema(description = "Updated address", example = "123 Main St, City, Country")
    private String address;

    @Valid
    @NotNull(message = "Authentication details are required")
    @Schema(description = "Authentication credentials")
    private AuthenticationDTO auth;
}
