package com.epam.dalvaradoc.mod2_spring_core_task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.TrainingType;

public interface TrainingTypeRepository extends JpaRepository<TrainingType, String> {
  TrainingType findByName(String name);
}
