package com.epam.dalvaradoc.mod2_spring_core_task.dao;

import java.sql.Date;

import jakarta.persistence.Entity;
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

  @Override
  public String toString() {
    return "Trainee [birthdate=" + birthdate + ", address=" + address + ", getFirstName()=" + getFirstName()
        + ", getLastName()=" + getLastName() + ", getPassword()=" + getPassword() + ", getUserId()=" + getUserId()
        + ", getUsername()=" + getUsername() + ", isActive()=" + isActive() + ", getClass()=" + getClass()
        + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
  }
}
