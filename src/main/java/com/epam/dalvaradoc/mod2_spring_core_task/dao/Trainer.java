package com.epam.dalvaradoc.mod2_spring_core_task.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Trainer extends User {
  private TrainingType specialization;

  public Trainer(String firstName, String lastName, String username, String password, boolean isActive, String userId,
      TrainingType specialization) {
    super(firstName, lastName, username, password, isActive, userId);
    this.specialization = specialization;
  }

  @Override
  public String toString() {
    return "Trainer [specialization=" + specialization + ", getSpecialization()=" + getSpecialization()
        + ", getFirstName()=" + getFirstName() + ", getLastName()=" + getLastName() + ", getPassword()=" + getPassword()
        + ", getUserId()=" + getUserId() + ", getUsername()=" + getUsername() + ", isActive()=" + isActive()
        + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
  }
}
