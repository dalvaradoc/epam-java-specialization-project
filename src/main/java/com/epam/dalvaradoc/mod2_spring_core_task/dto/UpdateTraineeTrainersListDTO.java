/* (C)2025 */
package com.epam.dalvaradoc.mod2_spring_core_task.dto;

import com.epam.dalvaradoc.mod2_spring_core_task.validations.UsernameConstraint;
import jakarta.validation.Valid;
import java.util.List;
import lombok.Getter;

@Getter
public class UpdateTraineeTrainersListDTO {
    @Valid private AuthenticationDTO auth;
    private List<@UsernameConstraint String> trainersUsernames;
}
