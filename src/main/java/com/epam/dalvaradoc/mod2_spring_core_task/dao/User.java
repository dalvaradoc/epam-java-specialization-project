package com.epam.dalvaradoc.mod2_spring_core_task.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "users")
public abstract class User {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String userId;
  private String firstName;
  private String lastName;
  private String username;
  private String password;
  private boolean isActive;
}
