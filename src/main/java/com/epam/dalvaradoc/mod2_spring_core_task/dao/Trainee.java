package com.epam.dalvaradoc.mod2_spring_core_task.dao;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Trainee extends User {
  private Date birthdate;
  private String address;
  
  public Trainee(String firstName, String lastName, String username, String password, boolean isActive, String userId,
      Date birthdate, String address) {
    super(firstName, lastName, username, password, isActive, userId);
    this.birthdate = birthdate;
    this.address = address;
  }
  @Override
  public String toString() {
    return "Trainee [birthdate=" + birthdate + ", address=" + address + ", getFirstName()=" + getFirstName()
        + ", getLastName()=" + getLastName() + ", getPassword()=" + getPassword() + ", getUserId()=" + getUserId()
        + ", getUsername()=" + getUsername() + ", isActive()=" + isActive() + ", getClass()=" + getClass()
        + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
  }
  
}
