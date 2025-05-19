package com.epam.dalvaradoc.mod2_spring_core_task.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "trainees")
public class Trainee extends User {

  private Date birthdate;

  private String address;

  @OneToMany(mappedBy = "trainee", cascade = jakarta.persistence.CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Training> trainings = new ArrayList<>();

  @ManyToMany
  @JoinTable(name = "trainee_trainers",
      joinColumns = @JoinColumn(name = "trainee_id"),
      inverseJoinColumns = @JoinColumn(name = "trainer_id"))
  private Set<Trainer> trainers = new HashSet<>();

  public Trainee(String firstName, String lastName, String username, String password, boolean isActive, String userId,
      Date birthdate, String address) {
    super(userId, firstName, lastName, username, password, isActive);
    this.birthdate = birthdate;
    this.address = address;
  }

  public Trainee(Trainee trainee) {
    super(trainee.getUserId(), trainee.getFirstName(), trainee.getLastName(), trainee.getUsername(),
        trainee.getPassword(), trainee.isActive());
    this.birthdate = trainee.getBirthdate();
    this.address = trainee.getAddress();
  }
}
