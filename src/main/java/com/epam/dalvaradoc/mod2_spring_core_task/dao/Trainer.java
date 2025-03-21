package com.epam.dalvaradoc.mod2_spring_core_task.dao;

public class Trainer extends User {
  private String specialization;

  public Trainer(String firstName, String lastName, String username, String password, boolean isActive, String userId,
      String specialization) {
    super(firstName, lastName, username, password, isActive, userId);
    this.specialization = specialization;
  }
}
