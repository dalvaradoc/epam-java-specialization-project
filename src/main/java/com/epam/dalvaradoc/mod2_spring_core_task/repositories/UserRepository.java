package com.epam.dalvaradoc.mod2_spring_core_task.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.User;

public interface UserRepository extends JpaRepository<User, String> {
  User findByUsername(String username);
  List<User> findByFirstNameAndLastName(String firstName, String lastName);
}
