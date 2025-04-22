package com.epam.dalvaradoc.mod2_spring_core_task.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Training;

public interface TrainingRepository extends JpaRepository<Training, String> {
  public Optional<Training> findByName(String name);
}
