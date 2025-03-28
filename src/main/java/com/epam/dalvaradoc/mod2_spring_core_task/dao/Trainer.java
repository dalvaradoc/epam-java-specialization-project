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

  public Trainer(Trainer trainer){
    super(trainer.getFirstName(), trainer.getLastName(), trainer.getUsername(), trainer.getPassword(), trainer.isActive(), trainer.getUserId());
    this.specialization = trainer.getSpecialization();
  }

  @Override
  public String toString() {
    return "Trainer [specialization=" + specialization + ", getSpecialization()=" + getSpecialization()
        + ", getFirstName()=" + getFirstName() + ", getLastName()=" + getLastName() + ", getPassword()=" + getPassword()
        + ", getUserId()=" + getUserId() + ", getUsername()=" + getUsername() + ", isActive()=" + isActive()
        + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((specialization == null) ? 0 : specialization.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    Trainer other = (Trainer) obj;
    if (specialization != other.specialization)
      return false;
    return true;
  }
}
