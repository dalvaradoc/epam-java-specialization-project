/* (C)2025 */
package com.epam.dalvaradoc.mod2_spring_core_task.dto;

import com.epam.dalvaradoc.mod2_spring_core_task.validations.NameLikeStringConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import java.sql.Date;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Data Transfer Object for Trainee information")
public class TraineeDTO {
    @Schema(description = "User ID", example = "123e4567-e89b-12d3-a456-426614174000")
    private String userId;

    @Valid
    @Schema(description = "Authentication credentials")
    private AuthenticationDTO auth;

    @NotBlank(message = "First name is required")
    @NameLikeStringConstraint
    @Schema(description = "Trainee's first name", example = "John")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @NameLikeStringConstraint
    @Schema(description = "Trainee's last name", example = "Doe")
    private String lastName;

    @Schema(description = "Whether the trainee account is active", example = "true")
    private Boolean isActive;

    @Past(message = "Birthdate must be in the past")
    @Schema(description = "Trainee's date of birth", example = "1990-01-01")
    private Date birthdate;

    @Length(min = 3, max = 50, message = "The address must be between 3 and 50 characters")
    @Schema(description = "Trainee's address", example = "123 Main St, City, Country")
    private String address;

    @Schema(description = "List of assigned trainers")
    private List<TrainerDTO> trainers;
}
