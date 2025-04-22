package com.epam.dalvaradoc.mod2_spring_core_task.dao;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "trainers")
public class Trainer extends User {
  @OneToOne(cascade = {
                        CascadeType.PERSIST,
                        CascadeType.MERGE
                      })
  @JoinColumn(name = "training_type_id", referencedColumnName = "trainingTypeId")
  private TrainingType specialization;

  public Trainer(String firstName, String lastName, String username, String password, boolean isActive, String userId,
      TrainingType specialization) {
    super(userId, firstName, lastName, username, password, isActive);
    this.specialization = specialization;
  }

  public Trainer(Trainer trainer){
    super(trainer.getUserId(), trainer.getFirstName(), trainer.getLastName(), trainer.getUsername(), trainer.getPassword(), trainer.isActive());
    this.specialization = trainer.getSpecialization();
  }

  @Override
  public String toString() {
    return "Trainer [specialization=" + specialization + ", getSpecialization()=" + getSpecialization()
        + ", getFirstName()=" + getFirstName() + ", getLastName()=" + getLastName() + ", getPassword()=" + getPassword()
        + ", getUserId()=" + getUserId() + ", getUsername()=" + getUsername() + ", isActive()=" + isActive()
        + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
  }
}
