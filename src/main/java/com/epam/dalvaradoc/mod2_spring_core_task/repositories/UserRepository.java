package com.epam.dalvaradoc.mod2_spring_core_task.repositories;

import java.util.List;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.User;

public interface UserRepository {
  List<User> findByFirstNameAndLastName(String firstName, String lastName);
}
