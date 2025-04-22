package com.epam.dalvaradoc.mod2_spring_core_task.dto;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.TrainingType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrainerDTO {
  private String userId;
  private String firstName;
  private String lastName;
  private String username;
  private String password;
  private boolean isActive;
  private TrainingType specialization;
}
