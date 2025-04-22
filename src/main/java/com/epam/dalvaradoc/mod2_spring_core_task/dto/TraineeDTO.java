package com.epam.dalvaradoc.mod2_spring_core_task.dto;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TraineeDTO {
  private String userId;
  private String firstName;
  private String lastName;
  private String username;
  private String password;
  private boolean isActive;
  private Date birthdate;
  private String address;
}
