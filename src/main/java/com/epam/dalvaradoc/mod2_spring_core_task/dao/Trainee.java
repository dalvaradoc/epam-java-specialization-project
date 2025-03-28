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

  public Trainee(Trainee trainee){
    super(trainee.getFirstName(), trainee.getLastName(), trainee.getUsername(), trainee.getPassword(), trainee.isActive(), trainee.getUserId());
    this.birthdate = trainee.getBirthdate();
    this.address = trainee.address;
  }

  @Override
  public String toString() {
    return "Trainee [birthdate=" + birthdate + ", address=" + address + ", getFirstName()=" + getFirstName()
        + ", getLastName()=" + getLastName() + ", getPassword()=" + getPassword() + ", getUserId()=" + getUserId()
        + ", getUsername()=" + getUsername() + ", isActive()=" + isActive() + ", getClass()=" + getClass()
        + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((birthdate == null) ? 0 : birthdate.hashCode());
    result = prime * result + ((address == null) ? 0 : address.hashCode());
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
    Trainee other = (Trainee) obj;
    if (birthdate == null) {
      if (other.birthdate != null)
        return false;
    } else if (!birthdate.equals(other.birthdate))
      return false;
    if (address == null) {
      if (other.address != null)
        return false;
    } else if (!address.equals(other.address))
      return false;
    return true;
  }  
}
