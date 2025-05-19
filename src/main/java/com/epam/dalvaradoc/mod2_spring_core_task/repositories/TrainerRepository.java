package com.epam.dalvaradoc.mod2_spring_core_task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainer;

public interface TrainerRepository extends JpaRepository<Trainer, String> {
  Trainer findByUsername(String username);
  void deleteByUsername(String username);
}
