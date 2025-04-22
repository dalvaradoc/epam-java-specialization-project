package com.epam.dalvaradoc.mod2_spring_core_task.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "trainers")
public class Trainer extends User {
  @ManyToOne
  @JoinColumn(name = "SPECIALIZATION")
  private TrainingType specialization;

  @OneToMany(mappedBy = "trainer")
  private List<Training> training = new ArrayList<>();

  @ManyToMany(mappedBy = "trainers")
  private Set<Trainee> trainees = new HashSet<>();

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
