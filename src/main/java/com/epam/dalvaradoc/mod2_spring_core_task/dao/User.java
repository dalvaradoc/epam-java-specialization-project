package com.epam.dalvaradoc.mod2_spring_core_task.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class User {
  private String firstName;
  private String lastName;
  private String username;
  private String password;
  private boolean isActive;
  private String userId;
}
